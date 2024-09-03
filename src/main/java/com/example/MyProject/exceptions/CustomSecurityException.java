package com.example.MyProject.exceptions;

public class CustomSecurityException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CustomSecurityException(String msg) {
		super(msg);
	}

}
