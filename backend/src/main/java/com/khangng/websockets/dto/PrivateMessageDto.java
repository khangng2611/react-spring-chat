package com.khangng.websockets.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class PrivateMessageDto {
    private int senderId;
    private int receiverId;
    private String content;
}
