package com.hcmut.chatterbox.service;

import com.hcmut.chatterbox.dto.request.GroupCreateRequestDTO;
import com.hcmut.chatterbox.dto.request.OneToOneCreateRequestDTO;
import com.hcmut.chatterbox.entity.Conversation;

import java.util.List;

public interface ConversationService {
    Conversation createGroup(GroupCreateRequestDTO requestDTO);
    Conversation createOneToOne(OneToOneCreateRequestDTO requestDTO);
}
