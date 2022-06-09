package com.selex.motor.comon.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@RestControllerAdvice()
public class RestExceptionHandler extends RuntimeException {


    private static final String MESSAGE_COMMAND_REGEX = "^\\{(.*?)}$";

    private final transient MessageSource messageSource;

    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    private String messageResolver(String message, Object[] args, Locale locale) {
        if (!message.matches(MESSAGE_COMMAND_REGEX)) return message;

        String resolved = message.substring(1, message.length() - 1);
        return messageSource.getMessage(resolved, args, locale);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionMessage handleArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String fieldName = error.getField();
            String errorMessage = messageResolver(error.getDefaultMessage(), null, locale);
            errors.put(fieldName, errorMessage);
        }

        return new ExceptionMessage(new Date(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
                messageResolver("{invalid.input}", null, locale), errors);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionMessage handleBadRequestException(BadRequestException ex, Locale locale) {
        return new ExceptionMessage(new Date(),
                ex.getStatusCode(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                messageResolver(ex.getMessage(), null, locale),
                null,
                ex.getErrorCode()
                );
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionMessage handleMultipartException(MultipartException ex, Locale locale) {
        return new ExceptionMessage(new Date(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), messageResolver(ex.getCause().getMessage(), null, locale));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionMessage handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, Locale locale) {
        return new ExceptionMessage(new Date(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), messageResolver(ex.getCause().getCause().getMessage(), null, locale));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionMessage handleConstraintViolationException(ConstraintViolationException ex, Locale locale) {
        String error = ex.getMessage();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<?> constraintViolation = constraintViolations.stream().findFirst().orElse(null);
            error = constraintViolation != null ? constraintViolation.getMessage() : error;
        }

        return new ExceptionMessage(new Date(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), messageResolver(error, null, locale));
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ResponseBody
    public ExceptionMessage handleForbiddenException(AccessDeniedException ex, Locale locale) {
        return new ExceptionMessage(new Date(), HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(), messageResolver(ex.getMessage(), null, locale));
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ExceptionMessage handleAuthorizationException(AuthorizationException ex, Locale locale) {
        return new ExceptionMessage(new Date(), HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), messageResolver(ex.getMessage(), null, locale));
    }

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionMessage handleResponseException(ResponseStatusException ex) {
        return new ExceptionMessage(new Date(), ex.getStatus().value(),
                ex.getStatus().getReasonPhrase(), ex.getReason());
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ExceptionMessage handleNullPointerException(NullPointerException ex) {
        return new ExceptionMessage(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Null pointer exception!");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionMessage handleNullPointerException(IllegalArgumentException ex, Locale locale) {
        return new ExceptionMessage(new Date(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), messageResolver(ex.getMessage(), null, locale));
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionMessage handleCredentialException(BadCredentialsException ex, Locale locale) {
        return new ExceptionMessage(new Date(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), messageResolver(ex.getMessage(), null, locale));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionMessage handleMissingRequestParameterException(MissingServletRequestParameterException ex) {
        return new ExceptionMessage(new Date(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
    }

    @ExceptionHandler({
            BindException.class
    })
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionMessage handleBindException(BindException e) {
        BindingResult result = e.getBindingResult();
        List<ObjectError> allErrors = result.getAllErrors();
        String messageError = allErrors.get(0).getDefaultMessage();
        if (Objects.isNull(messageError)) {
            messageError = "The input is not valid";
        }
        return new ExceptionMessage(new Date(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), messageError);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionMessage handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {

        return new ExceptionMessage(new Date(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionMessage handleUndeclaredThrowableException(UndeclaredThrowableException ex, Locale locale) {
        BadRequestException e = ((BadRequestException) ex.getUndeclaredThrowable());
        return new ExceptionMessage(new Date(), e.getStatusCode(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), messageResolver(e.getMessage(), null, locale));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionMessage handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, Locale locale) {
        return new ExceptionMessage(new Date(), HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), messageResolver("{type.is.not.correct}", null, locale));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ExceptionMessage handleException(Exception ex) {

        return new ExceptionMessage(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());
    }
}
