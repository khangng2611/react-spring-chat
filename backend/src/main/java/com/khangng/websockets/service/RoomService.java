package com.khangng.websockets.service;

import com.khangng.websockets.entity.Room;
import com.khangng.websockets.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    
    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }
    
    public Room getRoom (int senderId, int receiverId, boolean createIfNotExist) {
        Optional<Room> checkRoom = roomRepository.findBySenderIdAndReceiverId(senderId, receiverId);
        if (checkRoom.isPresent()) {
            return checkRoom.get();
        } else {
            if (createIfNotExist) {
                Room newRoom = createRoom(senderId, receiverId);
                return newRoom;
            }
            return null;
        }
    }
    
    private Room createRoom (int senderId, int receiverId) {
        Room senderReceiver = new Room();
        senderReceiver.setSenderId(senderId);
        senderReceiver.setReceiverId(receiverId);
        
        Room receiverSender = new Room();
        receiverSender.setSenderId(senderId);
        receiverSender.setReceiverId(receiverId);
        
        roomRepository.save(senderReceiver);
        roomRepository.save(receiverSender);
        return senderReceiver;
    }
}
