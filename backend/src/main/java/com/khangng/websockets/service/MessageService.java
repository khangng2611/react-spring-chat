package com.khangng.websockets.service;

import com.khangng.websockets.dto.PrivateMessageDto;
import com.khangng.websockets.dto.PublicMessageDto;
import com.khangng.websockets.entity.PrivateMessage;
import com.khangng.websockets.entity.PublicMessage;
import com.khangng.websockets.entity.Room;
import com.khangng.websockets.entity.User;
import com.khangng.websockets.repository.PrivateMessageRepository;
import com.khangng.websockets.repository.PublicMessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        User sender = userService.find(message.getSenderId());
        if (sender == null) {
            return null;
        }
        PublicMessage newMessage = new PublicMessage(
                sender,
                message.getContent()
        );
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
