package com.hcmut.chatterbox.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private int statusCode;
    private String message;
    private List<String> errors;
    private T data;
    
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                true,
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                new ArrayList<>(),
                data
        );
    }
    
    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(
                true,
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.getReasonPhrase(),
                new ArrayList<>(),
                data
        );
    }
    
    public static <T> ApiResponse<T> badRequest(String error) {
        return new ApiResponse<>(
                false,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                List.of(error),
                null
        );
    }
    
    public static <T> ApiResponse<T> badRequest(List<String> errors) {
        return new ApiResponse<>(
                false,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errors,
                null
        );
    }
    
    public static <T> ApiResponse<T> notFound(String error) {
        return new ApiResponse<>(
                false,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                List.of(error),
                null
        );
    }
    
    public static <T> ApiResponse<T> forbidden(String error) {
        return new ApiResponse<>(
                false,
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                List.of(error),
                null
        );
    }
    
    public static <T> ApiResponse<T> unauthorized(String error) {
        return new ApiResponse<>(
                false,
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                List.of(error),
                null
        );
    }
    
    public static <T> ApiResponse<T> internalServerError(String message) {
        return new ApiResponse<>(
                false,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ObjectUtils.isEmpty(message) ? HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() : message,
                new ArrayList<>(),
                null
        );
    }
}
