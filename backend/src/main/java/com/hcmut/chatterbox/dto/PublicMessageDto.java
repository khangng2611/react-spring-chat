package com.hcmut.chatterbox.dto;

import com.hcmut.chatterbox.entity.User;
import lombok.Data;

@Data
public class PublicMessageDto {
    private User sender;
    private String content;
}
