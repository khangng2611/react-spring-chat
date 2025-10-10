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
    private final UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserRegisterResponseDTO>> registerUser(@RequestBody @Valid UserRegisterRequestDTO request) {
        UserRegisterResponseDTO response = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }
    
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<?>> verifyOtp(@RequestBody @Valid UserVerifyOtpRequestDTO request) {
        userService.verifyOtp(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(null));
    }
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponseDTO>> login(@RequestBody @Valid LoginRequestDTO request) {
        TokenResponseDTO response = userService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
    }
    
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<TokenResponseDTO>> refreshToken(@RequestBody @Valid RefreshTokenRequestDTO request) {
        TokenResponseDTO response = userService.refreshToken(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
    }
}
