package com.example.product.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.example.product.entity.Comments;
import com.example.product.exception.CommentNotFoundException;
import com.example.product.exception.PostsNotFoundException;
import com.example.product.exception.UserNotFoundException;
import com.example.product.service.CommentService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
	@Autowired
	CommentService service;
	
	
	@GetMapping("/all")
	public ResponseEntity<List<Comments>> getAllComments(){
		return new ResponseEntity<List<Comments>>(service.getAllComments(),HttpStatus.OK);
	}
	
	@GetMapping("/{commentid}")
	public ResponseEntity<Comments> getCommentbyId(@PathVariable("commentid") int CommentId) throws CommentNotFoundException {
		return new ResponseEntity<Comments>(service.getCommentbyId(CommentId),HttpStatus.OK);
	}
	
	@PostMapping("/users/{userid}/posts/{postid}")
	public ResponseEntity<Void> createComment(@PathVariable("userid") int userid,@PathVariable("postid") int postid,@RequestBody Comments comments) throws UserNotFoundException,PostsNotFoundException{
      	   service.addComment(comments,userid,postid);
      	 return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	
	}
	
	@PutMapping("/update/{commentid}")
	public ResponseEntity<Comments> updateComment(@RequestBody Comments comments,@PathVariable("commentid") int commentId) throws CommentNotFoundException{
		return new ResponseEntity<Comments>(service.updateComment(comments,commentId),HttpStatus.ACCEPTED);		
	}
	
	@DeleteMapping("/delete/{commentid}")
	public ResponseEntity<Void> deleteComment(@PathVariable("commentid") int commentId) throws CommentNotFoundException{
		service.deleteComment(commentId);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
	
	
	
	@ExceptionHandler
	public ResponseEntity<ExceptionMessage> UserNotFoundException(UserNotFoundException ex) {
		ExceptionMessage message = new ExceptionMessage(ex.getMessage());
		return new ResponseEntity<ExceptionMessage>(message, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler
	public ResponseEntity<ExceptionMessage> postNotFoundExceptionEntity(PostsNotFoundException ex) {
		ExceptionMessage message = new ExceptionMessage(ex.getMessage());
		return new ResponseEntity<ExceptionMessage>(message, HttpStatus.NOT_FOUND);

	}
	
	@ExceptionHandler
	public ResponseEntity<ExceptionMessage> CommentNotFoundException(CommentNotFoundException ex) {
		ExceptionMessage message = new ExceptionMessage(ex.getMessage());
		return new ResponseEntity<ExceptionMessage>(message, HttpStatus.NOT_FOUND);

	}
	
	
	
	
 
}