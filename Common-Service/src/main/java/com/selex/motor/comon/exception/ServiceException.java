package com.selex.motor.comon.exception;

public abstract class ServiceException extends Exception {

	protected String message;
	protected int statusCode;
	protected String messageCode;

	public ServiceException(String message, int statusCode, String messageCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.messageCode = messageCode;
	}

	public ServiceException(String message, int statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getMessageCode() {
		return messageCode;
	}
}
