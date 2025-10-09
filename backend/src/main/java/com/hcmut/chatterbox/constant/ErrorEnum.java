package com.hcmut.chatterbox.constant;

public enum ErrorEnum {
    EMAIL_ALREADY_EXISTS("User with this email already exists"),
    INVALID_OTP("Invalid or expired OTP"),
    LOGIN_FAILED("Invalid email or password"),
    INACTIVE_ACCOUNT("Account not activated"),
    INVALID_REFRESH_TOKEN("Invalid refresh token");
    
    private final String message;
    
    ErrorEnum(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
