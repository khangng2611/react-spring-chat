package com.hcmut.chatterbox.constant;

public class Constants {
    // Redis Constants
    public static final String USER_REGISTER_OTP_PREFIX = "otp:";
    public static final long USER_REGISTER_OTP_TTL_MINUTES = 5;
    public static final String REFRESH_TOKEN_PREFIX = "refresh_token:";
    public static final long REFRESH_TOKEN_TTL_DAYS = 7;
    
    // File Constants
    public static final long FILE_MAX_SIZE = 10 * 1024 * 1024; // 10MB limit
    
    // Authentication Key Constants
    public static final String USER_ID_CLAIM = "userId";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";
    
    // Permitted URL Paths
    public static final String[] PERMITTED_URLS = {
        "/api/auth/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/swagger-ui.html"
    };
}
