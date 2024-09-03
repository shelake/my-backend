package com.example.product.exception;
 
public class MessageNotFoundException extends Exception {
	String message;
	
	
	public MessageNotFoundException(String message) {
	
		this.message = message;
	}
 
	public String getMessage() {
		return message;
	}
 
}
