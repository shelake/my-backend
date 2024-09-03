package com.example.product.exception;

public class PostsNotFoundException extends Exception{
    String message;
	
	
	public PostsNotFoundException(String message) {
	
		this.message = message;
	}
 
	public String getMessage() {
		return message;
	}

}
