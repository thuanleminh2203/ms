package com.selex.motor.comon.exception;

public class BadRequestException extends ServiceException {
    private String errorCode;
    private static final int HTTP_STATUS_CODE = 400;

    public BadRequestException(String message) {
        super(message, HTTP_STATUS_CODE);
    }

    public BadRequestException(String message, String errorCode) {
        super(message, HTTP_STATUS_CODE);
        this.errorCode = errorCode;
    }

    public BadRequestException(String message, int code) {
        super(message, code);
    }

    public String getErrorCode(){
        return errorCode;
    }
}
