package com.hcmut.chatterbox.constant;

public enum ErrorEnum {
    // Auth Error
    EMAIL_ALREADY_EXISTS("User with this email already exists"),
    INVALID_OTP("Invalid or expired OTP"),
    LOGIN_FAILED("Invalid email or password"),
    INACTIVE_ACCOUNT("Account not activated"),
    INVALID_REFRESH_TOKEN("Invalid refresh token"),
    
    // File Error
    INVALID_FILE_TYPE("Only image or video files are allowed"),
    INVALID_FILE_SIZE("File size exceeds 10MB"),
    
    // Message Error
    INVALID_CONVERSATION_OR_SENDER("Invalid conversation or sender");
    
    private final String message;
    
    ErrorEnum(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
