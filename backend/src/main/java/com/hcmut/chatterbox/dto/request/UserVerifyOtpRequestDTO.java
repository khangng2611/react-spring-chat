package com.hcmut.chatterbox.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserVerifyOtpRequestDTO {
    @NotEmpty(message = "The email address is required")
    @Email(message = "The email address is invalid", flags = { Pattern.Flag.CASE_INSENSITIVE })
    private String email;

    @NotEmpty(message = "The OTP is required")
    private String otp;
}