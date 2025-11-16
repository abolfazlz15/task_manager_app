package com.example.task_manager_app.auth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.task_manager_app.auth.dto.LoginDto;
import com.example.task_manager_app.auth.dto.RegisterRequest;
import com.example.task_manager_app.auth.dto.VerifyRegisterRequest;
import com.example.task_manager_app.auth.dto.VerifyRegisterResponse;
import com.example.task_manager_app.auth.interfaces.AuthService;
import com.example.task_manager_app.auth.interfaces.JwtService;
import com.example.task_manager_app.common.exception.BadRequestException;
import com.example.task_manager_app.common.service.DeviceParser;
import com.example.task_manager_app.otp.dto.CreateOtpDto;
import com.example.task_manager_app.otp.dto.VerifyOtpDto;
import com.example.task_manager_app.otp.enums.OtpOperationTypeEnum;
import com.example.task_manager_app.otp.enums.OtpTypeEnum;
import com.example.task_manager_app.otp.interfaces.OtpService;
import com.example.task_manager_app.user.dto.SessionRefreshTokenPayload;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.enums.UserRoleEnum;
import com.example.task_manager_app.user.enums.UserStatusEnum;
import com.example.task_manager_app.user.repository.UserRepository;
import com.example.task_manager_app.user.service.SessionServiceImpl;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final OtpService otpService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final SessionServiceImpl sessionServiceImpl;

    @Override
    public VerifyRegisterResponse login(LoginDto body, String ip, HttpHeaders headers) throws Exception {
        UserEntity user = userRepository.findByUsernameOrEmailOrPhone(body.getUsername(), body.getEmail(), body.getPhone());

        if(user == null) {
            throw new BadRequestException("Invalid identifier please verify your input and try again");
        }

        boolean isMatch = passwordEncoder.matches(body.getPassword(), user.getPassword());

        if(!isMatch) {
            throw new BadRequestException("invalid password");
        }

        return generateTokens(user, ip, headers);
    }


    @Override
    public void register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new BadRequestException("Email already in use");
        }
        CreateOtpDto otpTemplate = new CreateOtpDto(
            registerRequest.getEmail(),
            OtpTypeEnum.EMAIL,
            OtpOperationTypeEnum.REGISTER
        );
        String a = otpService.createOtp(otpTemplate);
        System.err.println("======== " + a);
    }

    @Override
    public VerifyRegisterResponse verifyRegister(VerifyRegisterRequest body, String ip, HttpHeaders headers) throws Exception {
        if (userRepository.findByEmail(body.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        otpService.verifyOtp(new VerifyOtpDto(
            body.getEmail(),
            body.getOtpCode(),
            OtpTypeEnum.EMAIL,
            OtpOperationTypeEnum.REGISTER
        ));

        UserEntity user = new UserEntity();
        user.setEmail(body.getEmail());
        user.setUsername(body.getUsername());
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        user.setRole(UserRoleEnum.USER);
        user.setStatus(UserStatusEnum.ACTIVE);
        userRepository.save(user);

        return generateTokens(user, ip, headers);
    }

    @Override
     public VerifyRegisterResponse generateTokens(UserEntity user, String ip, HttpHeaders headers) throws Exception {
         String refreshToken = generateRefreshToken(user, ip, headers);
         String accessToken = generateAccessToken(user);
         return new VerifyRegisterResponse(accessToken, refreshToken);
     }

     private String generateRefreshToken(UserEntity user, String ip, HttpHeaders headers) throws Exception {
        String userAgent = headers.getFirst(HttpHeaders.USER_AGENT);
        String device = DeviceParser.parseDevice(userAgent);

         return sessionServiceImpl.sign(new SessionRefreshTokenPayload(
             user.getId(),
             user.getRole(),
             ip,
             device,
             userAgent
         ));
     }

    private String generateAccessToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole().name());
        return jwtService.generateToken(
            claims,
            user.getEmail()
        );
    }

}
