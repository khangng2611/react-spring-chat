package com.khangng.websockets.repository;

import com.khangng.websockets.entity.Room;
import com.khangng.websockets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByFirstUserAndSecondUser(User firstUser, User secondUser);
}
