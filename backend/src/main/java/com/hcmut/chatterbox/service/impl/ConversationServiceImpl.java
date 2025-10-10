package com.hcmut.chatterbox.service.impl;

import com.hcmut.chatterbox.dto.response.ApiResponse;
import com.hcmut.chatterbox.entity.Conversation;
import com.hcmut.chatterbox.enums.ConversationType;
import com.hcmut.chatterbox.service.ConversationService;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class ConversationServiceImpl implements ConversationService {
    private final MongoTemplate mongoTemplate;
    
    @Override
    public ApiResponse<Conversation> getConversation(String conversationId) {
        return null;
    }
    
    @Override
    public ApiResponse<Conversation> createGroup(String name, String createdBy, List<String> participantIds) {
        Conversation conversation = new Conversation();
        conversation.setType(ConversationType.GROUP.name());
        conversation.setName(name);
        conversation.setCreatedBy(createdBy);
        conversation.setParticipants(participantIds);
        conversation.setCreatedAt(LocalDateTime.now());
        conversation.setUpdatedAt(LocalDateTime.now());
        Conversation createdConversation = mongoTemplate.save(conversation);
        return ApiResponse.success(createdConversation);
    }
    
    @Override
    public ApiResponse<Conversation> createOneToOne(String userId1, String userId2) {
        Conversation conversation = new Conversation();
        conversation.setType(ConversationType.ONE_TO_ONE.name());
        conversation.setParticipants(Arrays.asList(userId1, userId2));
        conversation.setCreatedAt(LocalDateTime.now());
        conversation.setUpdatedAt(LocalDateTime.now());
        Conversation createdConversation = mongoTemplate.save(conversation);
        return ApiResponse.success(createdConversation);
    }
}
