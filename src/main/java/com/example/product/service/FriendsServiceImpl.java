package com.example.product.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.product.entity.Friends;
import com.example.product.entity.Message;
import com.example.product.entity.Users;
import com.example.product.exception.FriendsNotFoundException;
import com.example.product.exception.UserNotFoundException;
import com.example.product.repository.FriendsRepository;
import com.example.product.repository.MessageRepository;

@Service
public class FriendsServiceImpl implements FriendsService{
	@Autowired
	FriendsRepository friendsRepository;
	@Autowired
	MessageRepository messageRepository;

	
	@Override
	public List<Message> getAllMessageBetweenFriends(int friendshipId)
			throws FriendsNotFoundException, UserNotFoundException {
		if(friendsRepository.findById(friendshipId).isEmpty())
			throw new FriendsNotFoundException("This frienship id "+friendshipId+" does not exist");

		Friends friends = friendsRepository.findById(friendshipId).get();
		
		Users user1=friends.getUserID1();
		Users user2=friends.getUserID2();
		if(user1==null) {
			throw new UserNotFoundException("This user id  "+user1+" does not exist");
		}
		if(user2==null) {
			throw new UserNotFoundException("This user id  "+user2+" does not exist");
		}
		//allMessages will save all the messages
		List<Message> allMessages = new ArrayList<>();
		for (Message message : user1.getSenderID()) {
	        if (message.getReceiverID().getUserID() == user2.getUserID()) {
	            allMessages.add(message);
	        }
	    }
		for (Message message : user2.getSenderID()) {
	        if (message.getReceiverID().getUserID() == user1.getUserID()) {
	            allMessages.add(message);
	        }
	    }
		return allMessages;
	}
 
 
	@Override
	public String sendMessageToAFriend(int friendshipId, String messagetext) throws FriendsNotFoundException, UserNotFoundException {
		Message newMessage=new Message();
		Friends friend=friendsRepository.findById(friendshipId).get();
		Users senderFriend=friend.getUserID1();
		Users recieverFriend=friend.getUserID2();
		newMessage.setMessage_text(messagetext);
		newMessage.setReceiverID(recieverFriend);
		newMessage.setSenderID(senderFriend);
		messageRepository.save(newMessage);
		return "Message sent successfully";
	}

}
