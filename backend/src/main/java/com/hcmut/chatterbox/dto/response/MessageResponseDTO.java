package com.hcmut.chatterbox.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MessageResponseDTO {
    private String id;
    private String conversationId;
    private String senderId;
    private String contentType;
    private String content;
    private String fileId;
    private String createdAt;
    private boolean isRead;
}
