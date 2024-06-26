package com.khangng.websockets.dto;

import lombok.Data;

@Data
public class PublicMessageDto {
    private int senderId;
    private String content;
}
