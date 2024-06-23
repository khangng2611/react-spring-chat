package com.khangng.websockets.dto;

import lombok.Data;

@Data
public class SignInDto {
    private String username;
    private String fullName;
    private String password;
}
