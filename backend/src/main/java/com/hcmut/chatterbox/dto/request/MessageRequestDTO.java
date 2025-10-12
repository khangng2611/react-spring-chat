package com.hcmut.chatterbox.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.hcmut.chatterbox.enums.ContentType;

@Data
public class MessageRequestDTO {
    @NotEmpty(message = "Conversation Id must not be null")
    private String conversationId;
    
    @NotEmpty(message = "Sender Id must not be null")
    private String senderId;

    @NotNull(message = "Content type must not be null")
    private ContentType contentType;

    // Text or file URL
    @NotEmpty(message = "Content must not be null")
    private String content;

    // GridFS file ID (optional)
    private String fileId;
}