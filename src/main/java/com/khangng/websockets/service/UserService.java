package com.khangng.websockets.service;

import com.khangng.websockets.config.Status;
import com.khangng.websockets.entity.User;
import com.khangng.websockets.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public void saveUser(User user) {
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
    }
    
    public void disconnect(User user) {
        Optional<User> checkUser = userRepository.findById(user.getId());
        if (checkUser.isPresent()) {
            User storedUser = checkUser.get();
            storedUser.setStatus(Status.OFFLINE);
            userRepository.save(storedUser);
        }
    }
    
    public List<User> getConnectedUsers() {
        return userRepository.findByStatus(Status.ONLINE);
    }
}
