package com.hcmut.chatterbox.util.converter;

import com.hcmut.chatterbox.dto.request.MessageRequestDTO;
import com.hcmut.chatterbox.dto.response.MessageResponseDTO;
import com.hcmut.chatterbox.entity.Message;

public class MessageConverter {
    public static Message toDocument (MessageRequestDTO messageDTO) {
        Message message = new Message();
        message.setConversationId(messageDTO.getConversationId());
        message.setSenderId(messageDTO.getSenderId());
        message.setContentType(messageDTO.getContentType().name());
        message.setContent(messageDTO.getContent());
        message.setFileId(messageDTO.getFileId());
        message.setRead(false);
        return message;
    }
    
    public static MessageResponseDTO toDTO (Message message) {
        MessageResponseDTO responseDTO = new MessageResponseDTO();
        responseDTO.setConversationId(message.getConversationId());
        responseDTO.setSenderId(message.getSenderId());
        responseDTO.setContentType(message.getContentType());
        responseDTO.setContent(message.getContent());
        responseDTO.setFileId(message.getFileId());
        responseDTO.setCreatedAt(message.getCreatedAt().toString());
        responseDTO.setRead(message.isRead());
        return responseDTO;
    }

}
