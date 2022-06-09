package com.selex.motor.comon.exception;

public class AuthorizationException extends ServiceException {
	
	private static final int HTTP_STATUS_CODE = 401;
	
	public AuthorizationException(String message) {
		super(message, HTTP_STATUS_CODE);
	}
}
