package com.hcmut.chatterbox.repository;

import com.hcmut.chatterbox.entity.Room;
import com.hcmut.chatterbox.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    Optional<Room> findByFirstUserAndSecondUser(User firstUser, User secondUser);
}
