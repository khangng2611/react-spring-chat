package com.khangng.websockets.service;

import com.khangng.websockets.config.Status;
import com.khangng.websockets.dto.SignInDto;
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
    
    public User connect(User user) {
        Optional<User> checkUser = userRepository.findById(user.getId());
        if (checkUser.isPresent()) {
            User storedUser = checkUser.get();
            storedUser.setStatus(Status.ONLINE);
            return userRepository.save(storedUser);
        }
        return null;
    }
    
    public User disconnect(User user) {
        Optional<User> checkUser = userRepository.findById(user.getId());
        if (checkUser.isPresent()) {
            User storedUser = checkUser.get();
            storedUser.setStatus(Status.OFFLINE);
            return userRepository.save(storedUser);
        }
        return null;
    }
    
    public List<User> getConnectedUsers() {
        return userRepository.findByStatus(Status.ONLINE);
    }
    
    public User loadUser(SignInDto signInDto) {
        User returnedUser = null;
        Optional<User> checkUser = userRepository.findByUsername(signInDto.getUsername());
        if (checkUser.isPresent()) {
            returnedUser = checkUser.get();
            return returnedUser;
        }
        returnedUser = new User(
                0,
                signInDto.getUsername(),
                signInDto.getPassword(),
                signInDto.getFullName(),
                Status.OFFLINE
        );
        return userRepository.save(returnedUser);
    }
}
