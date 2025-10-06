package com.hcmut.chatterbox.repository;

import com.hcmut.chatterbox.enums.UserStatus;
import com.hcmut.chatterbox.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{
//    List<User> findByStatus(UserStatus status);
    
//    Optional<User> findByUsername(String username);
}
