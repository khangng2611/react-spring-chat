package com.hcmut.chatterbox.service.impl;

import com.hcmut.chatterbox.dto.request.UserRegisterRequestDTO;
import com.hcmut.chatterbox.dto.response.ApiResponse;
import com.hcmut.chatterbox.dto.response.UserRegisterResponseDTO;
import com.hcmut.chatterbox.entity.User;
import com.hcmut.chatterbox.repository.UserRepository;
import com.hcmut.chatterbox.service.UserService;
import com.hcmut.chatterbox.util.converter.UserConverter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    
    public ApiResponse<UserRegisterResponseDTO> registerUser(UserRegisterRequestDTO registerRequestDTO) {
        User user = UserConverter.toEntity(registerRequestDTO);
        User createdUser = userRepository.save(user);
        UserRegisterResponseDTO responseDTO = UserConverter.toUserRegisterResponseDTO(createdUser);
        return ApiResponse.created(responseDTO);
    }
    
    public User connect(User user) {
        Optional<User> checkUser = userRepository.findById(user.getId());
        if (checkUser.isPresent()) {
            User storedUser = checkUser.get();
//            storedUser.setStatus(UserStatus.ONLINE);
            return userRepository.save(storedUser);
        }
        return null;
    }

    public User disconnect(User user) {
        Optional<User> checkUser = userRepository.findById(user.getId());
        if (checkUser.isPresent()) {
            User storedUser = checkUser.get();
//            storedUser.setStatus(UserStatus.OFFLINE);
            return userRepository.save(storedUser);
        }
        return null;
    }
    
//    public List<User> getConnectedUsers() {
//        return userRepository.findByStatus(UserStatus.ONLINE);
//    }
    
//    public User loadUser(SignInDTO signInDto) {
//        User returnedUser = null;
//        Optional<User> checkUser = userRepository.findByUsername(signInDto.getUsername());
//        if (checkUser.isPresent()) {
//            returnedUser = checkUser.get();
//            return returnedUser;
//        }
////        returnedUser = new User(
////                signInDto.getUsername(),
//////                signInDto.getPassword(),
////                signInDto.getFullName(),
////                UserStatus.OFFLINE
////        );
//        // todo
//        returnedUser = new User();
//        return userRepository.save(returnedUser);
//    }
    
    public User find(int id) {
        Optional<User> checkUser = userRepository.findById(id);
        if (checkUser.isPresent()) {
            return checkUser.get();
        }
        return null;
    }
}
