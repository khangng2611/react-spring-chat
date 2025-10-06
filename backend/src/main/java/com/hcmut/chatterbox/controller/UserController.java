package com.hcmut.chatterbox.controller;


import com.hcmut.chatterbox.dto.request.SignInDTO;
import com.hcmut.chatterbox.entity.User;
import com.hcmut.chatterbox.service.impl.UserServiceImpl;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    private UserServiceImpl userService;
    
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }
    
    @MessageMapping("/user.addUser")
    @SendTo("/online")
    public User addUser(@Payload  User user) {
        return userService.connect(user);
    }
    
//    @MessageMapping("/user.disconnectUser")
//    @SendTo("/online")
//    public User disconnect(@Payload  User user) {
//        return userService.disconnect(user);
//    }
//
//    @GetMapping("/users")
//    public List<User> getConnectedUsers() {
//        return userService.getConnectedUsers();
//    }
//
//    @PostMapping("/signin")
//    public User signIn (@RequestBody SignInDTO signInInfo ) {
//        return userService.loadUser(signInInfo);
//    }
    
}
