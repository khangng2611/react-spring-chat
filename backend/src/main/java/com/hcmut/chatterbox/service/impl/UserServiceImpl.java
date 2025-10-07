package com.hcmut.chatterbox.service.impl;

import com.hcmut.chatterbox.constant.Constants;
import com.hcmut.chatterbox.constant.ErrorEnum;
import com.hcmut.chatterbox.dto.request.UserRegisterRequestDTO;
import com.hcmut.chatterbox.dto.request.UserVerifyOtpRequestDTO;
import com.hcmut.chatterbox.dto.response.ApiResponse;
import com.hcmut.chatterbox.dto.response.UserRegisterResponseDTO;
import com.hcmut.chatterbox.entity.User;
import com.hcmut.chatterbox.enums.RegisterStatus;
import com.hcmut.chatterbox.exception.BizException;
import com.hcmut.chatterbox.repository.UserRepository;
import com.hcmut.chatterbox.service.UserService;
import com.hcmut.chatterbox.util.EmailService;
import com.hcmut.chatterbox.util.Utils;
import com.hcmut.chatterbox.util.converter.UserConverter;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final EmailService emailService;
    
    public ApiResponse<UserRegisterResponseDTO> registerUser(UserRegisterRequestDTO requestDTO) {
        // Check if email is registered
        Optional<User> existingUser = userRepository.findByEmail(requestDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new BizException(ErrorEnum.EMAIL_ALREADY_EXISTS);
        }
        
        // Create new user with requested email
        User user = UserConverter.toEntity(requestDTO);
        User createdUser = userRepository.save(user);
        
        // Generate OTP & save to Redis
        String otp = Utils.generateOtp();
        saveOtp(createdUser.getEmail(), otp);
        
        // Send OTP via Email
        try {
            emailService.sendOtpEmail(createdUser.getEmail(), otp);
        } catch (MessagingException ex) {
            log.error(ex.getMessage());
        }
        
        // Build response
        UserRegisterResponseDTO responseDTO = UserConverter.toUserRegisterResponseDTO(createdUser);
        return ApiResponse.created(responseDTO);
    }
    
    @Override
    @Transactional
    public ApiResponse<?> verifyOtp(UserVerifyOtpRequestDTO requestDTO) {
        String storedOtp = getOtp(requestDTO.getEmail());
        if (storedOtp == null || !storedOtp.equals(requestDTO.getOtp())) {
            throw new BizException(ErrorEnum.INVALID_OTP);
        }
        
        // Active user in database
        deleteOtp(requestDTO.getEmail());
        userRepository.updateRegisterStatusByEmail(requestDTO.getEmail(), RegisterStatus.ACTIVE);
        return ApiResponse.success(null);
    }

//    public void verify(VerifyRequest request) {
//        String storedOtp = getOtp(request.getIdentifier());
//        if (storedOtp == null || !storedOtp.equals(request.getOtp())) {
//            throw new IllegalArgumentException("Invalid or expired OTP");
//        }
//        // Update user status to ACTIVE
//        deleteOtp(request.getIdentifier());
//    }
    
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
    
    private void saveOtp(String email, String otp) {
        redisTemplate.opsForValue().set(
                Constants.USER_REGISTER_OTP_PREFIX + email,
                otp,
                Constants.USER_REGISTER_OTP_TTL_MINUTES,
                TimeUnit.MINUTES
        );
    }
    
    private String getOtp(String email) {
        return (String) redisTemplate.opsForValue().get(Constants.USER_REGISTER_OTP_PREFIX + email);
    }
    
    private void deleteOtp(String email) {
        redisTemplate.delete(Constants.USER_REGISTER_OTP_PREFIX + email);
    }
}
