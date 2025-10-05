package com.hcmut.chatterbox.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int statusCode;
    private boolean success;
    private String message;
    private T data;
    
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK.value(), true, HttpStatus.OK.getReasonPhrase(), data);
    }
    
    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(HttpStatus.CREATED.value(), true, HttpStatus.CREATED.getReasonPhrase(), data);
    }
    
    public static <T> ApiResponse<T> badRequest(String message) {
        return new ApiResponse<>(
                HttpStatus.BAD_REQUEST.value(),
                false,
                ObjectUtils.isEmpty(message) ? HttpStatus.BAD_REQUEST.getReasonPhrase() : message,
                null
        );
    }
    
    public static <T> ApiResponse<T> notFound(String message) {
        return new ApiResponse<>(
                HttpStatus.NOT_FOUND.value(),
                false,
                ObjectUtils.isEmpty(message) ? HttpStatus.NOT_FOUND.getReasonPhrase() : message,
                null
        );
    }
    
    public static <T> ApiResponse<T> forbidden(String message) {
        return new ApiResponse<>(
                HttpStatus.FORBIDDEN.value(),
                false,
                ObjectUtils.isEmpty(message) ? HttpStatus.FORBIDDEN.getReasonPhrase() : message,
                null
        );
    }
    
    public static <T> ApiResponse<T> unauthorized(String message) {
        return new ApiResponse<>(
                HttpStatus.UNAUTHORIZED.value(),
                false,
                ObjectUtils.isEmpty(message) ? HttpStatus.UNAUTHORIZED.getReasonPhrase() : message,
                null
        );
    }
    
    public static <T> ApiResponse<T> internalServerError(String message) {
        return new ApiResponse<>(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                false,
                ObjectUtils.isEmpty(message) ? HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase() : message,
                null
        );
    }
}
