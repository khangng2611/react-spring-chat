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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
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
    
    @Override
    public List<Message> getChatHistory(String conversationId, String userId, int page, int size) {
        // Validate conversation with userId
        Optional<Conversation> conversationOptional = conversationRepository.findById(conversationId);
        if (conversationOptional.isEmpty()) {
            throw new BizException(ErrorEnum.INVALID_CONVERSATION);
        }
        Conversation conversation = conversationOptional.get();
        if (!conversation.getParticipants().contains(userId)) {
            throw new BizException(ErrorEnum.CONVERSATION_NOT_INCLUDE_USER);
        }
        
        Query query = new Query(Criteria.where("conversationId").is(conversationId));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        query.with(pageable);
//        conversationRepository.findAll()
//        return conversationRepository.find(query, Message.class);
        return null;
    }
}