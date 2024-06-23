package com.khangng.websockets.controller;

import com.khangng.websockets.entity.Message;
import com.khangng.websockets.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MessageController {
    private MessageService messageService;
    private SimpMessagingTemplate simpMessagingTemplate;
    
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
    
    
    @MessageMapping("/chat")
    public void sendMessage(@Payload Message message) {
        Message savedMessage = messageService.save(message);
        simpMessagingTemplate.convertAndSendToUser(
            String.valueOf(savedMessage.getReceiverId()),
            "/queue/messages",
            savedMessage
        );
    }
    
    @GetMapping("/messages/{senderId}/{receiverId}")
    public List<Message> getMessages(@PathVariable int senderId, @PathVariable int receiverId) {
        return messageService.getMessages(senderId, receiverId);
    }
}
