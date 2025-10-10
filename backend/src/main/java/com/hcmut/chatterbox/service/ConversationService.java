package com.hcmut.chatterbox.service;

import com.hcmut.chatterbox.dto.response.ApiResponse;
import com.hcmut.chatterbox.entity.Conversation;

import java.util.List;

public interface ConversationService {
    ApiResponse<Conversation> getConversation(String conversationId);
    ApiResponse<Conversation> createGroup(String name, String createdBy, List<String> participantIds);
    ApiResponse<Conversation> createOneToOne(String userId1, String userId2);
}
