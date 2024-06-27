package com.khangng.websockets.dto;

import com.khangng.websockets.entity.User;
import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PrivateMessageDto {
    private User sender;
    private User receiver;
    private String content;
}
