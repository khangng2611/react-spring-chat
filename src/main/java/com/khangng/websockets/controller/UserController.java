package com.khangng.websockets.controller;


import com.khangng.websockets.entity.User;
import com.khangng.websockets.service.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    private UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    public User addUser(@Payload  User user) {
        userService.saveUser(user);
        return user;
    }
    
    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/topic")
    public User disconnect(@Payload  User user) {
        userService.disconnect(user);
        return user;
    }
    
    @GetMapping("/users")
    public List<User> getConnectedUsers() {
        return userService.getConnectedUsers();
    }
}
