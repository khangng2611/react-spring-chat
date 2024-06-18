package com.khangng.websockets.repository;

import com.khangng.websockets.config.Status;
import com.khangng.websockets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>{
    List<User> findByStatus(Status status);
}
