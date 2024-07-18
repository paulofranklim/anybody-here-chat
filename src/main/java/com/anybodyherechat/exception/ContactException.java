package com.anybodyherechat.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * {@link ContactException}
 * Purpose: Custom contact exception class
 */
@Getter
public class ContactException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ContactException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public ContactException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
