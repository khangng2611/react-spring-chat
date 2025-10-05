package com.hcmut.chatterbox.exception;

import com.hcmut.chatterbox.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BizNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(BizNotFoundException ex) {
        ApiResponse<?> notFoundResponse = ApiResponse.notFound(ex.getMessage());
        return new ResponseEntity<>(notFoundResponse, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(BizBadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BizBadRequestException ex) {
        ApiResponse<?> badRequestResponse = ApiResponse.badRequest(ex.getMessage());
        return new ResponseEntity<>(badRequestResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex) {
        ApiResponse<?> response = ApiResponse.internalServerError(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}