package com.hcmut.chatterbox.controller;

import com.hcmut.chatterbox.dto.request.LoginRequestDTO;
import com.hcmut.chatterbox.dto.request.RefreshTokenRequestDTO;
import com.hcmut.chatterbox.dto.request.UserRegisterRequestDTO;
import com.hcmut.chatterbox.dto.request.UserVerifyOtpRequestDTO;
import com.hcmut.chatterbox.dto.response.ApiResponse;
import com.hcmut.chatterbox.dto.response.TokenResponseDTO;
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
    public ResponseEntity<ApiResponse<UserRegisterResponseDTO>> registerUser(@RequestBody @Valid UserRegisterRequestDTO request) {
        ApiResponse<UserRegisterResponseDTO> response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatusCode())).body(response);
    }
    
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<?>> verifyOtp(@RequestBody @Valid UserVerifyOtpRequestDTO request) {
        ApiResponse<?> response = userService.verifyOtp(request);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatusCode())).body(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponseDTO>> login(@RequestBody @Valid LoginRequestDTO request) {
        ApiResponse<TokenResponseDTO> response = userService.login(request);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatusCode())).body(response);
    }
    
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<TokenResponseDTO>> refreshToken(@RequestBody @Valid RefreshTokenRequestDTO request) {
        ApiResponse<TokenResponseDTO> response = userService.refreshToken(request);
        return ResponseEntity.status(HttpStatus.valueOf(response.getStatusCode())).body(response);
    }
}
