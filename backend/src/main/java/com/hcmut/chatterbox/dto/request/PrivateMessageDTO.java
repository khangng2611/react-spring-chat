package com.hcmut.chatterbox.dto.request;

import com.hcmut.chatterbox.entity.User;
import lombok.Data;

@Data
public class PrivateMessageDTO {
    private User sender;
    private User receiver;
    private String content;
}
