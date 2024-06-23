package com.khangng.websockets.repository;

import com.khangng.websockets.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Integer> {
    
    List<Message> findByRoomId(int id);
}
