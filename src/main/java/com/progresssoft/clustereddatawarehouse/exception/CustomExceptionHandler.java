package com.progresssoft.clustereddatawarehouse.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Handles DuplicateDealException.
     *
     * @param e the exception
     * @return the error response
     */
    @ExceptionHandler(DuplicateDealException.class)
    public ResponseEntity<String> handleDuplicateDealException(DuplicateDealException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * Handles MethodArgumentNotValidException.
     *
     * @param e the exception
     * @return the error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
}