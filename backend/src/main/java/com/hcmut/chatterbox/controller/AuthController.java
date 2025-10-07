package com.hcmut.chatterbox.controller;

import com.hcmut.chatterbox.dto.request.UserRegisterRequestDTO;
import com.hcmut.chatterbox.dto.response.ApiResponse;
import com.hcmut.chatterbox.dto.response.UserRegisterResponseDTO;
import com.hcmut.chatterbox.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ApiResponse<UserRegisterResponseDTO>> registerUser(@Valid @RequestBody UserRegisterRequestDTO request) {
        ApiResponse<UserRegisterResponseDTO> response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
//    @PostMapping("/verify")
//    public ResponseEntity<String> verify(@RequestBody VerifyRequest request) {
//        userService.verify(request);
//        return ResponseEntity.ok("Account verified successfully.");
//    }
}
