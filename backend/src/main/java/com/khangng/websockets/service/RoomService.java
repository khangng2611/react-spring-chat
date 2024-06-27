package com.khangng.websockets.service;

import com.khangng.websockets.entity.Room;
import com.khangng.websockets.entity.User;
import com.khangng.websockets.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    private UserService userService;
    
    public RoomService(RoomRepository roomRepository, UserService userService) {
        this.roomRepository = roomRepository;
        this.userService = userService;
    }
    
    public Room getRoom (int userId1, int userId2, boolean createIfNotExist) {
        int firstUserId = Math.min(userId1, userId2);
        User firstUser = userService.find(firstUserId);
        int secondUserId = Math.max(userId1, userId2);
        User secondUser = userService.find(secondUserId);
        Optional<Room> checkRoom = roomRepository.findByFirstUserAndSecondUser(firstUser, secondUser);
        if (checkRoom.isPresent())
            return checkRoom.get();
        if (createIfNotExist)
            return createRoom(firstUser, secondUser);
        return null;
    }
    
    private Room createRoom (User firstUser, User secondUser) {
        Room newRoom = new Room();
        newRoom.setFirstUser(firstUser);
        newRoom.setSecondUser(secondUser);
        return roomRepository.save(newRoom);
    }
}
