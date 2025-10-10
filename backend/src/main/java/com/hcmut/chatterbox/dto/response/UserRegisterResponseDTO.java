package com.hcmut.chatterbox.dto.response;

import lombok.Data;

@Data
public class UserRegisterResponseDTO {
    private String id;
    private String email;
    private String fullName;
    private String registerStatus;
}
