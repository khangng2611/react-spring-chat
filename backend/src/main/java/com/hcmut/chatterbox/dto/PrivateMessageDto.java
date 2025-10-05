package com.hcmut.chatterbox.dto;

import com.hcmut.chatterbox.entity.User;
import lombok.Data;

@Data
public class PrivateMessageDto {
    private User sender;
    private User receiver;
    private String content;
}
