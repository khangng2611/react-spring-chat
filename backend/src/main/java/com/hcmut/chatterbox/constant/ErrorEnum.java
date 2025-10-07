package com.hcmut.chatterbox.constant;

public enum ErrorEnum {
    EMAIL_ALREADY_EXISTS("User with this email already exists"),
    INVALID_OTP("Invalid or expired OTP");
    
    private final String message;
    
    ErrorEnum(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
