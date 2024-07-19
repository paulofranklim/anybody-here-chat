package com.anybodyherechat.model;

import com.anybodyherechat.exception.ContactException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * {@link ApiExceptionDTO}
 * Purpose: Api exception model class
 */
public record ApiExceptionDTO(
        String message,
        Throwable cause,
        HttpStatus httpStatus,
        LocalDateTime timestamp
) {
    public static ApiExceptionDTO fromContactException(ContactException contactException) {
        return new ApiExceptionDTO(contactException.getMessage(), contactException, contactException.getHttpStatus(), LocalDateTime.now());
    }
}
