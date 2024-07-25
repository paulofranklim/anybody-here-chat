package com.anybodyherechat.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * {@link NewUserException}
 * Purpose: Custom contact exception class
 */
@Getter
public class NewUserException extends RuntimeException {

    private final HttpStatus httpStatus;

    public NewUserException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public NewUserException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
