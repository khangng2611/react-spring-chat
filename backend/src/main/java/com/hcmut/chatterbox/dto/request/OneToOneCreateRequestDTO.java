package com.hcmut.chatterbox.dto.request;

import lombok.Data;

@Data
public class OneToOneCreateRequestDTO {
    private String userId1;
    private String userId2;
}
