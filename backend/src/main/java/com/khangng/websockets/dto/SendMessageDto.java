package com.khangng.websockets.dto;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class SendMessageDto {
    @Column(name="sender_id")
    private int senderId;
    
    @Column(name="receiver_id")
    private int receiverId;
    
    @Column(name="content")
    private String content;
}
