package com.example.RedditClone.demo.Spring.exception;

public class SubRedditNotFoundException extends RuntimeException {

	public SubRedditNotFoundException(String message) {
		super(message);
	}
}
