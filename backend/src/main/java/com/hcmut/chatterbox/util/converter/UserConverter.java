package com.hcmut.chatterbox.util.converter;

import com.hcmut.chatterbox.dto.request.UserRegisterRequestDTO;
import com.hcmut.chatterbox.dto.response.UserRegisterResponseDTO;
import com.hcmut.chatterbox.entity.User;

public class UserConverter {
    public static User toEntity (UserRegisterRequestDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setHashPassword(userDTO.getHashedPassword());
        user.setFullName(userDTO.getFullName());
        return user;
    }
    
    public static UserRegisterResponseDTO toUserRegisterResponseDTO (User userEntity) {
        UserRegisterResponseDTO registerResponseDTO = new UserRegisterResponseDTO();
        registerResponseDTO.setEmail(userEntity.getEmail());
        registerResponseDTO.setPhone(userEntity.getPhone());
        registerResponseDTO.setFullName(userEntity.getFullName());
        return registerResponseDTO;
        
    }
}
