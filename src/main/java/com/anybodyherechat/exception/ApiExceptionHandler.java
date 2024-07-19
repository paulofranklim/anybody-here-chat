package com.anybodyherechat.exception;

import com.anybodyherechat.model.ApiExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * {@link ApiExceptionHandler}
 * Purpose: Custom exception handler class
 */
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ContactException.class})
    public ResponseEntity<ApiExceptionDTO> getContactException(ContactException contactException) {
        return new ResponseEntity<>(ApiExceptionDTO.fromContactException(contactException), contactException.getHttpStatus());
    }

}
