package com.hcmut.chatterbox.service;

import com.hcmut.chatterbox.dto.PrivateMessageDto;
import com.hcmut.chatterbox.dto.PublicMessageDto;
import com.hcmut.chatterbox.entity.PrivateMessage;
import com.hcmut.chatterbox.entity.PublicMessage;
import com.hcmut.chatterbox.entity.Room;
import com.hcmut.chatterbox.entity.User;
import com.hcmut.chatterbox.repository.PrivateMessageRepository;
import com.hcmut.chatterbox.repository.PublicMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private PrivateMessageRepository privateMessageRepository;
    private PublicMessageRepository publicMessageRepository;
    private RoomService roomService;
    private UserService userService;
    
    public MessageService(
        PrivateMessageRepository privateMessageRepository,
        PublicMessageRepository publicMessageRepository,
        RoomService roomService,
        UserService userService
    ) {
        this.privateMessageRepository = privateMessageRepository;
        this.publicMessageRepository = publicMessageRepository;
        this.roomService = roomService;
        this.userService = userService;
    }
    
    public PrivateMessage save(PrivateMessageDto message) {
        Room room = roomService.getRoom(
                message.getSender().getId(),
                message.getReceiver().getId(),
                true
        );
        if (room == null) return null;
        User sender = userService.find(message.getSender().getId());
        User receiver = userService.find(message.getReceiver().getId());
        if (sender == null || receiver == null ) return null;
        PrivateMessage newMessage = PrivateMessage.builder()
            .sender(sender)
            .receiver(receiver)
            .content(message.getContent())
            .roomId(room.getId())
            .build();
        return privateMessageRepository.save(newMessage);
    }
    
    public PublicMessage save(PublicMessageDto message) {
        User sender = userService.find(message.getSender().getId());
        if (sender == null) return null;
        PublicMessage newMessage = PublicMessage.builder()
            .sender(sender)
            .content(message.getContent())
            .build();
        return publicMessageRepository.save(newMessage);
    }
    
    public List<PrivateMessage> getMessages(int senderId, int receiverId) {
        Room room = roomService.getRoom(senderId, receiverId, false);
        if (room != null) {
            return privateMessageRepository.findByRoomId(room.getId());
        }
        return null;
    }
}
