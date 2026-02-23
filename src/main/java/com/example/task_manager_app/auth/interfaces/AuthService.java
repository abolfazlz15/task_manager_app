package com.example.task_manager_app.auth.interfaces;

import java.security.Principal;

import com.example.task_manager_app.auth.dto.*;
import org.springframework.http.HttpHeaders;

import com.example.task_manager_app.user.entity.UserEntity;

public interface AuthService {
    public void register(RegisterRequest registerRequest);
    public VerifyRegisterResponse verifyRegister(VerifyRegisterRequest verifyRegisterRequest, String ip, HttpHeaders headers) throws Exception;
    public VerifyRegisterResponse generateTokens(UserEntity user, String ip, HttpHeaders headers) throws Exception;
    public VerifyRegisterResponse login(LoginDto body, String ip, HttpHeaders headers) throws Exception;
    public VerifyRegisterResponse refreshToken(RefreshRequest refreshRequest) throws Exception;
    public void logout(Principal principal, LogoutRequest body) throws Exception;
    public void forgetPasswordSendOtp(ForgetPasswordSendOtpRequest body);
    public ForgetPasswordVerifyOtpResponse forgetPasswordVerifyOtp(ForgetPasswordVerifyOtpRequest body);
    public void forgetPassword(ForgetPasswordRequest body) throws Exception;
    public VerifyRegisterResponse loginAdmin(LoginDto body, String ip, HttpHeaders headers) throws Exception;
}
