package com.hcmut.chatterbox.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "conversations")
@Data
public class Conversation {
    @Id
    private String id;
    
    // "ONE_TO_ONE" or "GROUP"
    private String type;
    
    // List of user IDs
    private List<String> participants;
    
    // Group name (null for 1-1)
    private String name;
    
    // User ID who created the group
    private String createdBy;
    
    @CreatedDate
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    private LocalDateTime updatedAt;
    
}