package com.hcmut.chatterbox.service.impl;

import com.hcmut.chatterbox.dto.request.GroupCreateRequestDTO;
import com.hcmut.chatterbox.dto.request.OneToOneCreateRequestDTO;
import com.hcmut.chatterbox.entity.Conversation;
import com.hcmut.chatterbox.repository.ConversationRepository;
import com.hcmut.chatterbox.service.ConversationService;
import com.hcmut.chatterbox.util.converter.ConversationConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConversationServiceImpl implements ConversationService {
    private final ConversationRepository conversationRepository;
    
    @Override
    public Conversation createGroup(GroupCreateRequestDTO requestDTO) {
        Conversation conversation = ConversationConverter.toDocument(requestDTO);
        return conversationRepository.save(conversation);
    }
    
    @Override
    public Conversation createOneToOne(OneToOneCreateRequestDTO requestDTO) {
        Conversation conversation = ConversationConverter.toDocument(requestDTO);
        return conversationRepository.save(conversation);
    }
}
