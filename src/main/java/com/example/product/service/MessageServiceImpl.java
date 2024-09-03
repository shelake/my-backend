package com.example.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.product.entity.Message;
import com.example.product.entity.Users;
import com.example.product.exception.MessageNotFoundException;
import com.example.product.exception.UserNotFoundException;
import com.example.product.repository.MessageRepository;
import com.example.product.repository.UsersRepository;

import jakarta.transaction.Transactional;

@Service
public class MessageServiceImpl  implements MessageService{
	@Autowired
		private MessageRepository messagerepo;
		@Autowired
		private UsersRepository repo;
	 
		@Override
		public List<Message> getAllMessages() {
			return messagerepo.findAll();
		}
		@Override
		public Message getMessagebyId(int MessageId) throws MessageNotFoundException {
			if(messagerepo.findById(MessageId).isEmpty()) {
				throw new MessageNotFoundException("Message with id "+MessageId+" not found");
			}
			return messagerepo.findById(MessageId).get();	
		}
		@Override
		@Transactional
		public void deleteMessage(int MessageId) throws MessageNotFoundException {
			if(messagerepo.findById(MessageId).isEmpty()) {
				throw new MessageNotFoundException("Message with id "+MessageId+" not found");
			}
			messagerepo.deleteMessageNative(MessageId);
			
		}
		

		


		@Override
		@Transactional
		public String createMessage(int from, int to, String message) throws UserNotFoundException {
			if(repo.findById(from).isEmpty()||repo.findById(to).isEmpty()) {
				throw new UserNotFoundException("User Not Found");
				
			}
			Message newMessage = new Message();
			Users fromUser = repo.findById(from).get();
			Users toUser = repo.findById(to).get();
	 
			newMessage.setMessage_text(message);
			newMessage.setSenderID(fromUser);
			newMessage.setReceiverID(toUser);
	 
			messagerepo.save(newMessage);
			return "Message sent successfully";
	 
		}
		@Override
		@Transactional
		public Message UpdateMessage(Message message, int messageId) throws MessageNotFoundException{
			if(messagerepo.findById(messageId).isEmpty()) {
				throw new MessageNotFoundException("Message with id "+messageId+" not found");
			}
			Message Originalmessage=messagerepo.findById(messageId).get();
			Originalmessage.setMessage_text(message.getMessage_text());
			messagerepo.save(Originalmessage);		
			return Originalmessage;
		}

}
