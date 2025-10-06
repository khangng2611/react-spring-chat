package com.hcmut.chatterbox.dto.response;

import lombok.Data;

@Data
public class UserRegisterResponseDTO {
    private String email;
    private String phone;
    private String fullName;
}
