package com.hcmut.chatterbox.service.impl;

import com.hcmut.chatterbox.constant.Constants;
import com.hcmut.chatterbox.constant.ErrorEnum;
import com.hcmut.chatterbox.dto.request.LoginRequestDTO;
import com.hcmut.chatterbox.dto.request.RefreshTokenRequestDTO;
import com.hcmut.chatterbox.dto.request.UserRegisterRequestDTO;
import com.hcmut.chatterbox.dto.request.UserVerifyOtpRequestDTO;
import com.hcmut.chatterbox.dto.response.ApiResponse;
import com.hcmut.chatterbox.dto.response.TokenResponseDTO;
import com.hcmut.chatterbox.dto.response.UserRegisterResponseDTO;
import com.hcmut.chatterbox.entity.User;
import com.hcmut.chatterbox.enums.RegisterStatus;
import com.hcmut.chatterbox.exception.BizException;
import com.hcmut.chatterbox.repository.UserRepository;
import com.hcmut.chatterbox.service.UserService;
import com.hcmut.chatterbox.util.EmailService;
import com.hcmut.chatterbox.util.jwt.JwtService;
import com.hcmut.chatterbox.util.Utils;
import com.hcmut.chatterbox.util.converter.UserConverter;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    public ApiResponse<UserRegisterResponseDTO> registerUser(UserRegisterRequestDTO requestDTO) {
        // Check if email is registered
        Optional<User> existingUser = userRepository.findByEmail(requestDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new BizException(ErrorEnum.EMAIL_ALREADY_EXISTS);
        }
        
        // Create new user with requested email
        User user = UserConverter.toEntity(requestDTO);
        user.setHashPassword(hashPassword(requestDTO.getPassword()));
        User createdUser = userRepository.save(user);
        
        // Generate OTP & save to Redis
        String otp = Utils.generateOtp();
        redisTemplate.opsForValue().set(
                Constants.USER_REGISTER_OTP_PREFIX + createdUser.getEmail(),
                otp,
                Constants.USER_REGISTER_OTP_TTL_MINUTES,
                TimeUnit.MINUTES
        );
        
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
    public ApiResponse<String> verifyOtp(UserVerifyOtpRequestDTO requestDTO) {
        // Validate OTP
        String storedOtp = (String) redisTemplate.opsForValue().get(Constants.USER_REGISTER_OTP_PREFIX + requestDTO.getEmail());
        if (storedOtp == null || !storedOtp.equals(requestDTO.getOtp())) {
            throw new BizException(ErrorEnum.INVALID_OTP);
        }
        
        // Activate user & delete OTP in redis
        userRepository.updateRegisterStatusByEmail(requestDTO.getEmail(), RegisterStatus.ACTIVE);
        redisTemplate.delete(Constants.USER_REGISTER_OTP_PREFIX + requestDTO.getEmail());
        return ApiResponse.success(null);
    }
    
    @Override
    public ApiResponse<TokenResponseDTO> login(LoginRequestDTO request) {
        // Check if email is existed and valid password
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BizException(ErrorEnum.LOGIN_FAILED));
        boolean isPasswordValid = verifyPassword(request.getPassword(), user.getHashPassword());
        if (!isPasswordValid) {
            throw new BizException(ErrorEnum.LOGIN_FAILED);
        }
        
        // Validate user register status
        if (!RegisterStatus.ACTIVE.equals(user.getRegisterStatus())) {
            throw new BizException(ErrorEnum.INACTIVE_ACCOUNT);
        }
        
        // Generate token
        String accessToken = jwtService.generateAccessToken(request.getEmail());
        String refreshToken = jwtService.generateRefreshToken(request.getEmail());
        
        // Save refresh token to Redis
        redisTemplate.opsForValue().set(
                Constants.REFRESH_TOKEN_PREFIX + request.getEmail(),
                refreshToken,
                Constants.REFRESH_TOKEN_TTL_DAYS,
                TimeUnit.DAYS
        );
        
        TokenResponseDTO tokenResponse = new TokenResponseDTO(accessToken, refreshToken);
        return ApiResponse.success(tokenResponse);
    }
    
    @Override
    public ApiResponse<TokenResponseDTO> refreshToken(RefreshTokenRequestDTO request) {
        // Validate refreshToken from request with saved one in Redis
        String requestedRefreshToken = request.getRefreshToken();
        String email = jwtService.getEmailFromToken(requestedRefreshToken);
        String storedRefreshToken = (String) redisTemplate.opsForValue().get(Constants.REFRESH_TOKEN_PREFIX + email);
        
        if (storedRefreshToken == null || !storedRefreshToken.equals(requestedRefreshToken)) {
            throw new BizException(ErrorEnum.INVALID_REFRESH_TOKEN);
        }
        
        // Generate new accessToken
        String newAccessToken = jwtService.generateAccessToken(email);
        TokenResponseDTO tokenResponse = new TokenResponseDTO(newAccessToken, storedRefreshToken);
        return ApiResponse.success(tokenResponse);
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
    
    private String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    
    private boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
