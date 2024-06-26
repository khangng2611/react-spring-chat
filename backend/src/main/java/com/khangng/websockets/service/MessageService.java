package com.khangng.websockets.service;

import com.khangng.websockets.dto.PrivateMessageDto;
import com.khangng.websockets.dto.PublicMessageDto;
import com.khangng.websockets.entity.PrivateMessage;
import com.khangng.websockets.entity.PublicMessage;
import com.khangng.websockets.entity.Room;
import com.khangng.websockets.repository.PrivateMessageRepository;
import com.khangng.websockets.repository.PublicMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private PrivateMessageRepository privateMessageRepository;
    private PublicMessageRepository publicMessageRepository;
    
    private RoomService roomService;
    
    public MessageService(PrivateMessageRepository privateMessageRepository, PublicMessageRepository publicMessageRepository, RoomService roomService) {
        this.privateMessageRepository = privateMessageRepository;
        this.publicMessageRepository = publicMessageRepository;
        this.roomService = roomService;
    }
    
    public PrivateMessage save(PrivateMessageDto message) {
        Room room = roomService.getRoom(
                message.getSenderId(),
                message.getReceiverId(),
                true
        );
        if (room != null) {
            PrivateMessage newMessage = PrivateMessage.builder()
                .senderId(message.getSenderId())
                .receiverId(message.getReceiverId())
                .content(message.getContent())
                .roomId(room.getId())
                .build();
            return privateMessageRepository.save(newMessage);
        }
        return null;
    }
    
    public PublicMessage save(PublicMessageDto message) {
        PublicMessage newMessage = PublicMessage.builder()
                .senderId(message.getSenderId())
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
