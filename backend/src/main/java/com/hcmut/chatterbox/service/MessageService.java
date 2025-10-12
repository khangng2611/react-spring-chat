package com.hcmut.chatterbox.service;

import com.hcmut.chatterbox.dto.request.MessageRequestDTO;
import com.hcmut.chatterbox.entity.Message;

import java.util.List;

public interface MessageService {
    void sendMessage (MessageRequestDTO message);
    List<Message> getChatHistory (String conversationId, String userId, int page, int size);
}
