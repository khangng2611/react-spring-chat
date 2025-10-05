package com.hcmut.chatterbox.controller;

import com.hcmut.chatterbox.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request) {
//        userService.register(request);
        // convert to DTO response -> build api response -> return
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful. Please verify your email/phone.");
    }
    
//    @PostMapping("/verify")
//    public ResponseEntity<String> verify(@RequestBody VerifyRequest request) {
//        userService.verify(request);
//        return ResponseEntity.ok("Account verified successfully.");
//    }
}
