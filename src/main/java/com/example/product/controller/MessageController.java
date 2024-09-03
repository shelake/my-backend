package com.example.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.product.dto.ExceptionMessage;
import com.example.product.entity.Message;
import com.example.product.exception.MessageNotFoundException;
import com.example.product.exception.PostsNotFoundException;
import com.example.product.exception.UserNotFoundException;
import com.example.product.service.MessageService;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/message")
public class MessageController {
	@Autowired
	MessageService messageservice;

	@GetMapping("/all")
	public ResponseEntity<List<Message>> getAllMessages() {
		return new ResponseEntity<List<Message>>(messageservice.getAllMessages(), HttpStatus.OK);
	}

	@GetMapping("/{messageid}")
	public ResponseEntity<Message> getMessagebyId(@PathVariable("messageid") int MessageId)
			throws MessageNotFoundException {
		return new ResponseEntity<Message>(messageservice.getMessagebyId(MessageId), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{messageid}")
	public ResponseEntity<Void> deleteMessage(@PathVariable("messageid") int MessageId)
			throws MessageNotFoundException {
		messageservice.deleteMessage(MessageId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}

	@PostMapping("/{userId}/{message}/create/{otherUserId}")
	public ResponseEntity<String> createMessage(@PathVariable int userId, @PathVariable int otherUserId,
			@PathVariable String message) throws UserNotFoundException {
		String result = messageservice.createMessage(userId, otherUserId, message);
		return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
	}

	@PutMapping("/update/{messageid}")
	public ResponseEntity<Message> UpdateMessage(@RequestBody Message message, @PathVariable("messageid") int MessageId)
			throws MessageNotFoundException {
		return new ResponseEntity<Message>(messageservice.UpdateMessage(message, MessageId), HttpStatus.ACCEPTED);
	}

	@ExceptionHandler
	public ResponseEntity<ExceptionMessage> UserNotFoundException(UserNotFoundException ex) {
		ExceptionMessage message = new ExceptionMessage(ex.getMessage());
		return new ResponseEntity<ExceptionMessage>(message, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler
	public ResponseEntity<ExceptionMessage> MessageNotFoundException(MessageNotFoundException ex) {
		ExceptionMessage message = new ExceptionMessage(ex.getMessage());
		return new ResponseEntity<ExceptionMessage>(message, HttpStatus.NOT_FOUND);

	}

}
