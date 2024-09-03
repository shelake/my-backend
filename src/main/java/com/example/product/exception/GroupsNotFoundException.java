package com.example.product.exception;
 
public class GroupsNotFoundException extends Exception {
	String message;
	
	
	public GroupsNotFoundException(String message) {
	
		this.message = message;
	}
 
	public String getMessage() {
		return message;
	}
 
}