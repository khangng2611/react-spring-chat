package com.hcmut.chatterbox.entity;

import lombok.Data;
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
    
    // "TEXT", "IMAGE", "VIDEO"
    private String contentType;
    
    // Text or file URL
    private String content;
    
    // GridFS file ID
    private String fileId;
    
    private LocalDateTime createdAt;
    
    private boolean isRead;
    
}