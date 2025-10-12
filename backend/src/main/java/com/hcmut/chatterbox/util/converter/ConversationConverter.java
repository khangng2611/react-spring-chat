package com.hcmut.chatterbox.util.converter;

import com.hcmut.chatterbox.dto.request.GroupCreateRequestDTO;
import com.hcmut.chatterbox.dto.request.OneToOneCreateRequestDTO;
import com.hcmut.chatterbox.entity.Conversation;
import com.hcmut.chatterbox.enums.ConversationType;

import java.util.Arrays;

public class ConversationConverter {
    public static Conversation toDocument (OneToOneCreateRequestDTO requestDTO) {
        Conversation conversation = new Conversation();
        conversation.setType(ConversationType.ONE_TO_ONE.name());
        conversation.setParticipants(Arrays.asList(requestDTO.getUserId1(), requestDTO.getUserId2()));
        return conversation;
    }
    
    public static Conversation toDocument (GroupCreateRequestDTO requestDTO) {
        Conversation conversation = new Conversation();
        conversation.setType(ConversationType.GROUP.name());
        conversation.setName(requestDTO.getName());
        conversation.setCreatedBy(requestDTO.getCreatedBy());
        conversation.setParticipants(requestDTO.getParticipantIds());
        return conversation;
    }

}
