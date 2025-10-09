package com.hcmut.chatterbox.controller;

import com.hcmut.chatterbox.dto.request.PrivateMessageDTO;
import com.hcmut.chatterbox.dto.request.PublicMessageDTO;
import com.hcmut.chatterbox.entity.PrivateMessage;
import com.hcmut.chatterbox.entity.PublicMessage;
import com.hcmut.chatterbox.entity.User;
import com.hcmut.chatterbox.service.impl.MessageService;
import com.hcmut.chatterbox.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class MessageController {
    private MessageService messageService;
    private UserServiceImpl userService;
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    public MessageController(MessageService messageService, UserServiceImpl userService, SimpMessagingTemplate simpMessagingTemplate) {
        this.messageService = messageService;
        this.userService = userService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    
    
    @MessageMapping("/chat/private")
    public void sendMessage(@Payload PrivateMessageDTO message) {
        PrivateMessage savedMessage = messageService.save(message);
        User receiver = userService.find(message.getReceiver().getId());
        simpMessagingTemplate.convertAndSendToUser(
            String.valueOf(receiver.getId()),
            "/queue/messages",
            savedMessage
        );
    }
    
//    @MessageMapping("/chat/public")
//    @SendTo("/public")
//    public PublicMessage sendMessage(@Payload PublicMessageDTO message) {
//        return messageService.save(message);
//    }
    
    @GetMapping("/api/messages/{senderId}/{receiverId}")
    public List<PrivateMessage> getMessages(@PathVariable int senderId, @PathVariable int receiverId) {
        return messageService.getMessages(senderId, receiverId);
    }
}
