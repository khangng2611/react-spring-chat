package com.hcmut.chatterbox.repository;

import com.hcmut.chatterbox.enums.UserStatus;
import com.hcmut.chatterbox.entity.User;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(@Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email address") String email);
//    List<User> findByStatus(UserStatus status);
    
//    Optional<User> findByUsername(String username);
}
