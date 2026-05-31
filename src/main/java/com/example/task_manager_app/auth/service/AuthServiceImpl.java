package com.example.task_manager_app.auth.service;

import java.security.Principal;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import com.example.task_manager_app.auth.dto.*;
import com.example.task_manager_app.common.exception.ForbiddenException;
import com.example.task_manager_app.user.enums.PrivilegeEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.task_manager_app.auth.entity.RoleEntity;
import com.example.task_manager_app.auth.interfaces.AuthService;
import com.example.task_manager_app.auth.interfaces.JwtService;
import com.example.task_manager_app.common.exception.BadRequestException;
import com.example.task_manager_app.common.exception.NotFoundException;
import com.example.task_manager_app.common.service.DeviceParser;
import com.example.task_manager_app.otp.dto.CreateOtpDTO;
import com.example.task_manager_app.otp.dto.VerifyOtpDTO;
import com.example.task_manager_app.otp.enums.OtpOperationTypeEnum;
import com.example.task_manager_app.otp.enums.OtpTypeEnum;
import com.example.task_manager_app.otp.interfaces.OtpService;
import com.example.task_manager_app.user.dto.RefreshTokenPayloadDTO;
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
    public VerifyRegisterResponseDTO login(LoginRequestDTO body, String ip, HttpHeaders headers) throws Exception {
        UserEntity user = userRepository.findByUsernameOrEmailOrPhone(body.getUsername(), body.getEmail(), body.getPhone());

        if(user == null) {
            throw new BadRequestException("Invalid identifier please verify your input and try again");
        }

        if(!user.getPrivilege().equals(PrivilegeEnum.USER)) {
            throw new NotFoundException("User not found");
        }

        if(!user.getStatus().equals(UserStatusEnum.ACTIVE)) {
            throw new ForbiddenException("User is not registered");
        }

        boolean isMatch = passwordEncoder.matches(body.getPassword(), user.getPassword());

        if(!isMatch) {
            throw new BadRequestException("invalid password");
        }

        return generateTokens(user, ip, headers);
    }



    @Override
    public void register(RegisterRequestDTO registerRequestDTO) {
        if (userRepository.findByEmail(registerRequestDTO.getEmail()).isPresent()) {
            throw new BadRequestException("Email already in use");
        }
        CreateOtpDTO otpTemplate = new CreateOtpDTO(
            registerRequestDTO.getEmail(),
            OtpTypeEnum.EMAIL,
            OtpOperationTypeEnum.REGISTER
        );
        String a = otpService.createOtp(otpTemplate);
        System.err.println("======== " + a);
    }

    @Override
    public VerifyRegisterResponseDTO verifyRegister(VerifyRegisterRequestDTO body, String ip, HttpHeaders headers) throws Exception {
        if (userRepository.findByEmail(body.getEmail()).isPresent()) {
            throw new BadRequestException("Email already in use");
        }
        if (userRepository.findByUsername(body.getUsername()).isPresent()) {
            throw new BadRequestException("Username already in use");
        }
        otpService.verifyOtp(new VerifyOtpDTO(
            body.getEmail(),
            body.getOtpCode(),
            OtpTypeEnum.EMAIL,
            OtpOperationTypeEnum.REGISTER
        ));

        UserEntity user = new UserEntity();
        user.setEmail(body.getEmail());
        user.setUsername(body.getUsername());
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        user.setStatus(UserStatusEnum.ACTIVE);
        userRepository.save(user);

        return generateTokens(user, ip, headers);
    }

    @Override
     public VerifyRegisterResponseDTO generateTokens(UserEntity user, String ip, HttpHeaders headers) throws Exception {
         String refreshToken = generateRefreshToken(user, ip, headers);
         String accessToken = generateAccessToken(user);
         return new VerifyRegisterResponseDTO(accessToken, refreshToken);
     }

    @Override
    public void logout(Principal principal, RefreshTokenRequestDTO body) throws Exception {
        boolean isDeleted = sessionService.revoke(Long.parseLong(principal.getName()), body.getRefreshToken());

        if (!isDeleted) {
           throw new NotFoundException("Session not found");
        }
    }

    private String generateRefreshToken(UserEntity user, String ip, HttpHeaders headers) throws Exception {
        String userAgent = headers.getFirst(HttpHeaders.USER_AGENT);
        String device = DeviceParser.parseDevice(userAgent);

         return sessionService.sign(new RefreshTokenPayloadDTO(
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

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("authorities", roleIds);
        return jwtService.generateToken(
            claims,
            user.getId().toString()
        );
    }
    @Override
    public VerifyRegisterResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) throws Exception {
        SessionEntity session = sessionService.verify(refreshTokenRequestDTO.getRefreshToken());
        UserEntity user = userRepository.findById(
            session.getUser().getId()
        ).orElseThrow(() -> new NotFoundException("User not found"));
        return new VerifyRegisterResponseDTO(
            generateAccessToken(user),
            refreshTokenRequestDTO.getRefreshToken()
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
    public void forgetPasswordSendOtp(ForgetPasswordSendOtpRequestDTO body) {
        String otpId = body.getOtpType() == OtpTypeEnum.EMAIL
                ? body.getEmail()
                : body.getCellphone();

        UserEntity user = getUserByOtp(body.getOtpType(), otpId);
        if (user == null) {
            throw new NotFoundException("user not found");
        }

        // create otp
        String otp = otpService.createOtp(
                new CreateOtpDTO(
                        otpId,
                        body.getOtpType(),
                        OtpOperationTypeEnum.FORGET_PASSWORD
                )
        );

        // otpService.sendOtp(body.getOtpType(), otpId, otp);
        System.out.println("otpId: " + otp);
    }

    @Override
    public ForgetPasswordVerifyOtpResponseDTO forgetPasswordVerifyOtp(ForgetPasswordVerifyOtpRequestDTO body) {

        String otpId = body.getOtpType() == OtpTypeEnum.EMAIL
            ? body.getEmail()
            : body.getCellphone();

        UserEntity user = getUserByOtp(body.getOtpType(), otpId);
        if (user == null) {
            throw new NotFoundException("user not found");
        }

        otpService.verifyOtp(new VerifyOtpDTO(
            otpId,
            body.getOtpCode(),
            body.getOtpType(),
            OtpOperationTypeEnum.FORGET_PASSWORD
        ));

        String token = otpService.createOtp(
            new CreateOtpDTO(
                otpId,
                OtpTypeEnum.TOKEN,
                OtpOperationTypeEnum.FORGET_PASSWORD
            )
        );

        return new ForgetPasswordVerifyOtpResponseDTO(token);
    }


    @Override
    public void resetPassword(ResetPasswordRequestDTO body) {
        String otpId = body.getOtpType() == OtpTypeEnum.EMAIL
                ? body.getEmail()
                : body.getCellphone();

        UserEntity user = getUserByOtp(body.getOtpType(), otpId);
        if (user == null) {
            throw new NotFoundException("user not found");
        }

        otpService.verifyOtp(new VerifyOtpDTO(
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
    public VerifyRegisterResponseDTO loginAdmin(LoginRequestDTO body, String ip, HttpHeaders headers) throws Exception {
        UserEntity user = userRepository.findByUsernameOrEmailOrPhone(body.getUsername(), body.getEmail(), body.getPhone());

        if(user == null) {
            throw new BadRequestException("Invalid identifier please verify your input and try again");
        }

        if(!user.getStatus().equals(UserStatusEnum.ACTIVE)) {
            throw new ForbiddenException("Admin is not registered");
        }

        if(user.getPrivilege().equals(PrivilegeEnum.ADMIN) || user.getPrivilege().equals(PrivilegeEnum.SUPER_ADMIN)) {
            boolean isMatch = passwordEncoder.matches(body.getPassword(), user.getPassword());

            if(!isMatch) {
                throw new BadRequestException("Invalid password");
            }

            return generateTokens(user, ip, headers);
        }

        throw new ForbiddenException("Access denied");
    }

}
