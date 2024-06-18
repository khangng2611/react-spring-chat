package com.khangng.websockets.service;

import com.khangng.websockets.entity.Message;
import com.khangng.websockets.entity.Room;
import com.khangng.websockets.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private RoomService roomService;
    
    public MessageService(MessageRepository messageRepository, RoomService roomService) {
        this.messageRepository = messageRepository;
        this.roomService = roomService;
    }
    
    public Message save(Message message) {
        Room room = roomService.getRoom(
                message.getSenderId(),
                message.getReceiverId(),
                true
        );
        if (room != null) {
            message.setRoomId(room.getId());
            return messageRepository.save(message);
        }
        // throw exception
        return null;
    }
    
    public List<Message> getMessages(int senderId, int receiverId) {
        Room room = roomService.getRoom(senderId, receiverId, false);
        if (room != null) {
            return messageRepository.findByRoomId(room.getId());
        }
        return null;
    }
}
