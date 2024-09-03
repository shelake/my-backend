package com.example.product.exception;

public class UserNotFoundException extends Exception {
	String message;

	public UserNotFoundException(String message) {
	
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	
	
	
	
	

}
