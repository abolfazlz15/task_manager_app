package com.example.task_manager_app.auth.service;

import java.security.Principal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import com.example.task_manager_app.common.exception.ForbiddenException;
import com.example.task_manager_app.user.enums.UserRoleEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.task_manager_app.auth.dto.ForgetPasswordRequest;
import com.example.task_manager_app.auth.dto.ForgetPasswordSendOtpRequest;
import com.example.task_manager_app.auth.dto.ForgetPasswordVerifyOtpRequest;
import com.example.task_manager_app.auth.dto.ForgetPasswordVerifyOtpResponse;
import com.example.task_manager_app.auth.dto.LoginDto;
import com.example.task_manager_app.auth.dto.LogoutRequest;
import com.example.task_manager_app.auth.dto.RefreshRequest;
import com.example.task_manager_app.auth.dto.RegisterRequest;
import com.example.task_manager_app.auth.dto.VerifyRegisterRequest;
import com.example.task_manager_app.auth.dto.VerifyRegisterResponse;
import com.example.task_manager_app.auth.entity.RoleEntity;
import com.example.task_manager_app.auth.interfaces.AuthService;
import com.example.task_manager_app.auth.interfaces.JwtService;
import com.example.task_manager_app.common.exception.BadRequestException;
import com.example.task_manager_app.common.exception.NotFoundException;
import com.example.task_manager_app.common.service.DeviceParser;
import com.example.task_manager_app.otp.dto.CreateOtpDto;
import com.example.task_manager_app.otp.dto.VerifyOtpDto;
import com.example.task_manager_app.otp.enums.OtpOperationTypeEnum;
import com.example.task_manager_app.otp.enums.OtpTypeEnum;
import com.example.task_manager_app.otp.interfaces.OtpService;
import com.example.task_manager_app.user.dto.SessionRefreshTokenPayload;
import com.example.task_manager_app.user.entity.SessionEntity;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.enums.UserStatusEnum;
import com.example.task_manager_app.user.interfaces.SessionService;
import com.example.task_manager_app.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final OtpService otpService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;

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
            throw new BadRequestException("Email already in use");
        }
        if (userRepository.findByUsername(body.getUsername()).isPresent()) {
            throw new BadRequestException("Username already in use");
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
        user.setIsSuperuser(false);
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

    @Override
    public void logout(Principal principal, LogoutRequest body) throws Exception {
        boolean isDeleted = sessionService.revoke(Long.parseLong(principal.getName()), body.getRefreshToken());

        if (!isDeleted) {
           throw new NotFoundException("Session not found");
        }
    }

    private String generateRefreshToken(UserEntity user, String ip, HttpHeaders headers) throws Exception {
        String userAgent = headers.getFirst(HttpHeaders.USER_AGENT);
        String device = DeviceParser.parseDevice(userAgent);

         return sessionService.sign(new SessionRefreshTokenPayload(
             user.getId(),
             ip,
             device,
             userAgent
         ));
     }

    private String generateAccessToken(UserEntity user) {
        List<Long> roleIds = Optional.ofNullable(user.getRoles())
                .orElse(Collections.emptySet())
                .stream()
                .map(RoleEntity::getId)
                .distinct()
                .collect(Collectors.toList());

// if you use flag:
//        if (user.getIsSuperuser()) {
//            roleIds.add("SUPERUSER");
//        }
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("authorities", roleIds);
        return jwtService.generateToken(
            claims,
            user.getId().toString()
        );
    }
    @Override
    public VerifyRegisterResponse refreshToken(RefreshRequest refreshRequest) throws Exception {
        SessionEntity session = sessionService.verify(refreshRequest.getRefreshToken());
        UserEntity user = userRepository.findById(
            session.getUser().getId()
        ).orElseThrow(() -> new NotFoundException("User not found"));
        return new VerifyRegisterResponse(
            generateAccessToken(user),
            refreshRequest.getRefreshToken()
        );
    }

    private UserEntity getUserByOtp(OtpTypeEnum otpType, String otpId) {
        return switch (otpType) {
            case EMAIL -> userRepository.findByEmail(otpId).orElse(null);
            case SMS -> userRepository.findByPhone(otpId).orElse(null);
            default -> throw new BadRequestException("Invalid otp type");
        };
    }

    @Override
    public void forgetPasswordSendOtp(ForgetPasswordSendOtpRequest body) {
        String otpId = body.getOtpType() == OtpTypeEnum.EMAIL
                ? body.getEmail()
                : body.getCellphone();

        UserEntity user = getUserByOtp(body.getOtpType(), otpId);
        if (user == null) {
            throw new NotFoundException("user not found");
        }

        // create otp
        String otp = otpService.createOtp(
                new CreateOtpDto(
                        otpId,
                        body.getOtpType(),
                        OtpOperationTypeEnum.FORGET_PASSWORD
                )
        );

        // otpService.sendOtp(body.getOtpType(), otpId, otp);
        System.out.println("otpId: " + otp);
    }

    @Override
    public ForgetPasswordVerifyOtpResponse forgetPasswordVerifyOtp(ForgetPasswordVerifyOtpRequest body) {

        String otpId = body.getOtpType() == OtpTypeEnum.EMAIL
            ? body.getEmail()
            : body.getCellphone();

        UserEntity user = getUserByOtp(body.getOtpType(), otpId);
        if (user == null) {
            throw new NotFoundException("user not found");
        }

        otpService.verifyOtp(new VerifyOtpDto(
            otpId,
            body.getOtpCode(),
            body.getOtpType(),
            OtpOperationTypeEnum.FORGET_PASSWORD
        ));

        String token = otpService.createOtp(
            new CreateOtpDto(
                otpId,
                OtpTypeEnum.TOKEN,
                OtpOperationTypeEnum.FORGET_PASSWORD
            )
        );

        return new ForgetPasswordVerifyOtpResponse(token);
    }


    @Override
    public void forgetPassword(ForgetPasswordRequest body) {
        String otpId = body.getOtpType() == OtpTypeEnum.EMAIL
                ? body.getEmail()
                : body.getCellphone();

        UserEntity user = getUserByOtp(body.getOtpType(), otpId);
        if (user == null) {
            throw new NotFoundException("user not found");
        }

        otpService.verifyOtp(new VerifyOtpDto(
                otpId,
                body.getToken(),
                OtpTypeEnum.TOKEN,
                OtpOperationTypeEnum.FORGET_PASSWORD
        ));

        sessionService.purgeUserActiveTokens(user.getId());

        String hashedPassword = passwordEncoder.encode(body.getPassword());
        user.setPassword(hashedPassword);
        user.setPasswordUpdateAt(Instant.now());

        userRepository.save(user);
    }

    @Override
    public VerifyRegisterResponse loginAdmin(LoginDto body, String ip, HttpHeaders headers) throws Exception {
        UserEntity user = userRepository.findByUsernameOrEmailOrPhone(body.getUsername(), body.getEmail(), body.getPhone());

        if(user == null) {
            throw new BadRequestException("Invalid identifier please verify your input and try again");
        }

        if(!user.getStatus().equals(UserStatusEnum.ACTIVE)) {
            throw new ForbiddenException("Admin is not registered");
        }

        Optional<RoleEntity> role = user.getRoles().stream().findFirst();

        if(role.isPresent() || user.getIsSuperuser()) {
            boolean isMatch = passwordEncoder.matches(body.getPassword(), user.getPassword());

            if(!isMatch) {
                throw new BadRequestException("Invalid password");
            }

            return generateTokens(user, ip, headers);
        }

        throw new ForbiddenException("Access denied");
    }

}
