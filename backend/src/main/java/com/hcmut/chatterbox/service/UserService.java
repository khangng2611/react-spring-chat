package com.hcmut.chatterbox.service;

import com.hcmut.chatterbox.dto.request.LoginRequestDTO;
import com.hcmut.chatterbox.dto.request.RefreshTokenRequestDTO;
import com.hcmut.chatterbox.dto.request.UserRegisterRequestDTO;
import com.hcmut.chatterbox.dto.request.UserVerifyOtpRequestDTO;
import com.hcmut.chatterbox.dto.response.ApiResponse;
import com.hcmut.chatterbox.dto.response.TokenResponseDTO;
import com.hcmut.chatterbox.dto.response.UserRegisterResponseDTO;

public interface UserService {
    ApiResponse<UserRegisterResponseDTO> registerUser(UserRegisterRequestDTO request);
    ApiResponse<String> verifyOtp(UserVerifyOtpRequestDTO request);
    ApiResponse<TokenResponseDTO> login(LoginRequestDTO request);
    ApiResponse<TokenResponseDTO> refreshToken(RefreshTokenRequestDTO request);
}
