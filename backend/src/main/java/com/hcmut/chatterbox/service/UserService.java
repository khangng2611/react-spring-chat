package com.hcmut.chatterbox.service;

import com.hcmut.chatterbox.dto.request.LoginRequestDTO;
import com.hcmut.chatterbox.dto.request.RefreshTokenRequestDTO;
import com.hcmut.chatterbox.dto.request.UserRegisterRequestDTO;
import com.hcmut.chatterbox.dto.request.UserVerifyOtpRequestDTO;
import com.hcmut.chatterbox.dto.response.TokenResponseDTO;
import com.hcmut.chatterbox.dto.response.UserRegisterResponseDTO;

public interface UserService {
    UserRegisterResponseDTO registerUser(UserRegisterRequestDTO request);
    void verifyOtp(UserVerifyOtpRequestDTO request);
    TokenResponseDTO login(LoginRequestDTO request);
    TokenResponseDTO refreshToken(RefreshTokenRequestDTO request);
}
