package com.hcmut.chatterbox.service;

import com.hcmut.chatterbox.entity.Conversation;

import java.util.List;

public interface ConversationService {
    Conversation getConversation(String conversationId);
    Conversation createGroup(String name, String createdBy, List<String> participantIds);
    Conversation createOneToOne(String userId1, String userId2);
}
