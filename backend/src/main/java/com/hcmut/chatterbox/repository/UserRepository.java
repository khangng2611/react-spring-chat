package com.hcmut.chatterbox.repository;

import com.hcmut.chatterbox.entity.User;
import com.hcmut.chatterbox.enums.RegisterStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);
    
    @Query("update User u set u.registerStatus = :status where u.email = :email")
    @Modifying
    void updateRegisterStatusByEmail(String email, RegisterStatus status);

//    List<User> findByStatus(UserStatus status);
//    Optional<User> findByUsername(String username);
}
