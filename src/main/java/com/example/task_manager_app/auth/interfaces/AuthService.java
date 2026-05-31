package com.example.task_manager_app.auth.interfaces;

import java.security.Principal;

import com.example.task_manager_app.auth.dto.*;
import org.springframework.http.HttpHeaders;

import com.example.task_manager_app.user.entity.UserEntity;

public interface AuthService {
    public void register(RegisterRequestDTO registerRequestDTO);
    public VerifyRegisterResponseDTO verifyRegister(VerifyRegisterRequestDTO verifyRegisterRequestDTO, String ip, HttpHeaders headers) throws Exception;
    public VerifyRegisterResponseDTO generateTokens(UserEntity user, String ip, HttpHeaders headers) throws Exception;
    public VerifyRegisterResponseDTO login(LoginRequestDTO body, String ip, HttpHeaders headers) throws Exception;
    public VerifyRegisterResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) throws Exception;
    public void logout(Principal principal, RefreshTokenRequestDTO body) throws Exception;
    public void forgetPasswordSendOtp(ForgetPasswordSendOtpRequestDTO body);
    public ForgetPasswordVerifyOtpResponseDTO forgetPasswordVerifyOtp(ForgetPasswordVerifyOtpRequestDTO body);
    public void resetPassword(ResetPasswordRequestDTO body) throws Exception;
    public VerifyRegisterResponseDTO loginAdmin(LoginRequestDTO body, String ip, HttpHeaders headers) throws Exception;
}
