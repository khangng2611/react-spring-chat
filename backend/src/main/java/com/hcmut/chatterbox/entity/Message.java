package com.hcmut.chatterbox.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "messages")
@Data
public class Message {
    @Id
    private String id;
    
    private String conversationId;
    
    private String senderId;
    
    private String contentType;
    
    private String content;
    
    private String fileId;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    private boolean isRead;
    
}