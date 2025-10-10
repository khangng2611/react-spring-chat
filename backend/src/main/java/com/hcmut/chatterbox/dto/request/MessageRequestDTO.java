package com.hcmut.chatterbox.dto.request;

import lombok.Data;

@Data
public class MessageRequestDTO {
    private String conversationId;

    private String senderId;

    // "TEXT", "IMAGE", "VIDEO"
    private String contentType;

    // Text or file URL
    private String content;

    // GridFS file ID (optional)
    private String fileId;
}