package com.hcmut.chatterbox.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RefreshTokenRequestDTO {
    @NotEmpty(message = "Refresh token is required")
    private String refreshToken;
}
