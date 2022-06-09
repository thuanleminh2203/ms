package com.selex.motor.comon.exception;

public class BusinessException extends ServiceException {
	
	private static final int HTTP_STATUS_CODE = 400;
	
	public BusinessException(String message) {
		super(message, HTTP_STATUS_CODE);
	}
}
