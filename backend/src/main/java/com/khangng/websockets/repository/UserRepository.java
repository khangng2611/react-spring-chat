package com.khangng.websockets.repository;

import com.khangng.websockets.config.Status;
import com.khangng.websockets.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{
    List<User> findByStatus(Status status);
    
    Optional<User> findByUsername(String username);
}
