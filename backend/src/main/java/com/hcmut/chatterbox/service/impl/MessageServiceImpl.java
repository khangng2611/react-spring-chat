package com.hcmut.chatterbox.service.impl;

import com.hcmut.chatterbox.constant.ErrorEnum;
import com.hcmut.chatterbox.dto.request.MessageRequestDTO;
import com.hcmut.chatterbox.dto.response.MessageResponseDTO;
import com.hcmut.chatterbox.entity.Conversation;
import com.hcmut.chatterbox.entity.Message;
import com.hcmut.chatterbox.enums.ConversationType;
import com.hcmut.chatterbox.exception.BizException;
import com.hcmut.chatterbox.repository.ConversationRepository;
import com.hcmut.chatterbox.repository.MessageRepository;
import com.hcmut.chatterbox.service.MessageService;
import com.hcmut.chatterbox.util.converter.MessageConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    
    @Override
    public void sendMessage(MessageRequestDTO messageDTO) {
        // Validate conversation and sender
        Optional<Conversation> conversationOptional = conversationRepository.findById(messageDTO.getConversationId());
        if (conversationOptional.isEmpty()) {
            throw new BizException(ErrorEnum.INVALID_CONVERSATION);
        }
        Conversation conversation = conversationOptional.get();
        if (!conversation.getParticipants().contains(messageDTO.getSenderId())) {
            throw new BizException(ErrorEnum.INVALID_SENDER);
        }
        // Save message to DB
        Message savedMessage = messageRepository.save(MessageConverter.toDocument(messageDTO));
        
        // Broadcast message
        MessageResponseDTO messageResponseDTO = MessageConverter.toDTO(savedMessage);
        if (ConversationType.ONE_TO_ONE.name().equals(conversation.getType())) {
            String receiverId = conversation.getParticipants().stream()
                    .filter(id -> !id.equals(messageResponseDTO.getSenderId()))
                    .findFirst()
                    .orElseThrow(() -> new BizException(ErrorEnum.INVALID_RECEIVER));
            simpMessagingTemplate.convertAndSendToUser(
                    receiverId,
                    "/queue/messages",
                    messageResponseDTO
            );
        } else {
            // Group chat: Send to all participants except sender
            for (String participantId : conversation.getParticipants()) {
                if (!participantId.equals(messageResponseDTO.getSenderId())) {
                    simpMessagingTemplate.convertAndSendToUser(
                            participantId,
                            "/queue/messages",
                            messageResponseDTO
                    );
                }
            }
        }
    }
}