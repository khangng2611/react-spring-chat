package com.hcmut.chatterbox.controller;

import com.hcmut.chatterbox.dto.request.MessageRequestDTO;
import com.hcmut.chatterbox.entity.Message;
import com.hcmut.chatterbox.service.ConversationService;
import com.hcmut.chatterbox.service.impl.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public class ChatController {
    private final MessageService messageService;
    private final ConversationService conversationService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    
    
    @MessageMapping("/chat")
    public void sendPrivateMessage(@Payload MessageRequestDTO message) {
        Message savedMessage = messageService.save(message);
//        Conversation conversation = conversationService.findConversation(message.getConversationId());
//        if (conversation.getType().equals("ONE_TO_ONE")) {
//            String receiverId = conversation.getParticipants().stream()
//                    .filter(id -> !id.equals(message.getSenderId()))
//                    .findFirst()
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid receiver"));
//            simpMessagingTemplate.convertAndSendToUser(
//                    receiverId,
//                    "/queue/messages",
//                    savedMessage
//            );
//        } else {
//            // Group chat: Send to all participants except sender
//            for (String participantId : conversation.getParticipants()) {
//                if (!participantId.equals(message.getSenderId())) {
//                    simpMessagingTemplate.convertAndSendToUser(
//                            participantId,
//                            "/queue/messages",
//                            savedMessage
//                    );
//                }
//            }
//        }
    }
    
//    @MessageMapping("/chat/public")
//    @SendTo("/public")
//    public PublicMessage sendMessage(@Payload PublicMessageDTO message) {
//        return messageService.save(message);
//    }
    
//    @GetMapping("/api/messages/{senderId}/{receiverId}")
//    public List<PrivateMessage> getMessages(@PathVariable int senderId, @PathVariable int receiverId) {
//        return messageService.getMessages(senderId, receiverId);
//    }
}
