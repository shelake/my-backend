package com.example.product.service;

import java.util.List;

import com.example.product.entity.Message;
import com.example.product.exception.FriendsNotFoundException;
import com.example.product.exception.UserNotFoundException;

public interface FriendsService {
	List<Message> getAllMessageBetweenFriends(int friendshipId) throws FriendsNotFoundException, UserNotFoundException;
	
	String sendMessageToAFriend(int friendshipId, String messages) throws FriendsNotFoundException, UserNotFoundException;

}
