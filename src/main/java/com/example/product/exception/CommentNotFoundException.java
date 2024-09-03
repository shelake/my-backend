package com.example.product.exception;

public class CommentNotFoundException extends Exception {
     String message;
	
	

 
	public CommentNotFoundException(String message) {
		super();
		this.message = message;
	}




	public String getMessage() {
		return message;
	}

}
