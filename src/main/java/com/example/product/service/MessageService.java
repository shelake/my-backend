package com.example.product.service;

import java.util.List;

import com.example.product.entity.Message;
import com.example.product.exception.MessageNotFoundException;
import com.example.product.exception.UserNotFoundException;

public interface MessageService {
	List<Message> getAllMessages();
	Message getMessagebyId(int MessageId) throws MessageNotFoundException ;
	void deleteMessage(int MessageId) throws MessageNotFoundException;
	String createMessage(int from, int to, String message) throws UserNotFoundException;
	Message UpdateMessage(Message message, int messageId) throws MessageNotFoundException;

}
