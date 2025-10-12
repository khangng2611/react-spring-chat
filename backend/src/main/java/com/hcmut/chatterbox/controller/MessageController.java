package com.hcmut.chatterbox.controller;

import com.hcmut.chatterbox.dto.request.MessageRequestDTO;
import com.hcmut.chatterbox.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;
    
    @MessageMapping("/message")
    public void sendMessage(@Payload MessageRequestDTO message) {
        messageService.sendMessage(message);
    }
    
//    @GetMapping("/api/messages/{senderId}/{receiverId}")
//    public List<PrivateMessage> getMessages(@PathVariable int senderId, @PathVariable int receiverId) {
//        return messageService.getMessages(senderId, receiverId);
//    }
}
