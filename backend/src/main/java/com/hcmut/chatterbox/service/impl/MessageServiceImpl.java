package com.hcmut.chatterbox.service.impl;

import com.hcmut.chatterbox.constant.ErrorEnum;
import com.hcmut.chatterbox.dto.request.MessageRequestDTO;
import com.hcmut.chatterbox.entity.Conversation;
import com.hcmut.chatterbox.entity.Message;
import com.hcmut.chatterbox.exception.BizException;
import com.hcmut.chatterbox.service.MessageService;
import com.hcmut.chatterbox.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MongoTemplate mongoTemplate;
    private final UserService userService;
    
    public Message save(MessageRequestDTO messageDTO) {
        // Validate conversation and sender
        Conversation conversation = mongoTemplate.findById(messageDTO.getConversationId(), Conversation.class);
        if (conversation == null || !conversation.getParticipants().contains(messageDTO.getSenderId())) {
            throw new BizException(ErrorEnum.INVALID_CONVERSATION_OR_SENDER);
        }
        
        Message message = new Message();
        message.setConversationId(messageDTO.getConversationId());
        message.setSenderId(messageDTO.getSenderId());
        message.setContentType(messageDTO.getContentType());
        message.setContent(messageDTO.getContent());
        message.setFileId(messageDTO.getFileId());
        message.setCreatedAt(LocalDateTime.now());
        message.setRead(false);
        
        return mongoTemplate.save(message);
    }
}