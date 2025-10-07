package com.hcmut.chatterbox.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRegisterRequestDTO {
    @NotEmpty(message = "The full name is required")
    @Size(min = 2, max = 100, message = "The length of full name must be between 2 and 100 characters.")
    private String fullName;
    
    @NotEmpty(message = "The email address is required.")
    @Email(message = "The email address is invalid.", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;
    
    @NotEmpty(message = "Hashed password is required")
    private String hashedPassword;
}