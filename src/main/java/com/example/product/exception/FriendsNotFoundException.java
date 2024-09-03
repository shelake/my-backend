package com.example.product.exception;
 
public class FriendsNotFoundException extends Exception {
	String message;
	
	
	public FriendsNotFoundException(String message) {
	
		this.message = message;
	}
 
	public String getMessage() {
		return message;
	}
 
}
