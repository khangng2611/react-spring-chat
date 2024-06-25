package com.khangng.websockets.controller;

import com.khangng.websockets.dto.SendMessageDto;
import com.khangng.websockets.entity.Message;
import com.khangng.websockets.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@Controller
@RestController
@CrossOrigin
public class MessageController {
    private MessageService messageService;
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    public MessageController(MessageService messageService, SimpMessagingTemplate simpMessagingTemplate) {
        this.messageService = messageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    
    
    @MessageMapping("/chat")
    public void sendMessage(@Payload SendMessageDto message) {
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
