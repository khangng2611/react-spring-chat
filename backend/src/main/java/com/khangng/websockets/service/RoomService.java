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
    
    public Room getRoom (int userId1, int userId2, boolean createIfNotExist) {
        int firstUserId = Math.min(userId1, userId2);
        int secondUserId = Math.max(userId1, userId2);
        Optional<Room> checkRoom = roomRepository.findByFirstUserIdAndSecondUserId(firstUserId, secondUserId);
        if (checkRoom.isPresent()) {
            return checkRoom.get();
        } else {
            if (createIfNotExist) {
                Room newRoom = createRoom(firstUserId, secondUserId);
                return newRoom;
            }
            return null;
        }
    }
    
    private Room createRoom (int firstUserId, int secondUserId) {
        Room newRoom = new Room();
        newRoom.setFirstUserId(firstUserId);
        newRoom.setSecondUserId(secondUserId);
        return roomRepository.save(newRoom);
    }
}
