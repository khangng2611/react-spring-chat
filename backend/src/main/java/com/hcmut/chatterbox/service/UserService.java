package com.hcmut.chatterbox.service;

import com.hcmut.chatterbox.dto.request.UserRegisterRequestDTO;
import com.hcmut.chatterbox.dto.request.UserVerifyOtpRequestDTO;
import com.hcmut.chatterbox.dto.response.ApiResponse;
import com.hcmut.chatterbox.dto.response.UserRegisterResponseDTO;

public interface UserService {
    ApiResponse<UserRegisterResponseDTO> registerUser(UserRegisterRequestDTO registerRequestDTO);
    ApiResponse<?> verifyOtp(UserVerifyOtpRequestDTO verifyOtpRequestDTO);
}
