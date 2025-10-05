package com.hcmut.chatterbox.repository;

import com.hcmut.chatterbox.config.Status;
import com.hcmut.chatterbox.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{
    List<User> findByStatus(Status status);
    
    Optional<User> findByUsername(String username);
}
