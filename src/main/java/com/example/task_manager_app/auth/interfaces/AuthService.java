package com.example.task_manager_app.auth.interfaces;

import com.example.task_manager_app.auth.dto.*;
import org.springframework.http.HttpHeaders;

import com.example.task_manager_app.user.entity.UserEntity;

public interface AuthService {
    public void register(RegisterRequest registerRequest);
    public VerifyRegisterResponse verifyRegister(VerifyRegisterRequest verifyRegisterRequest, String ip, HttpHeaders headers) throws Exception;
    public VerifyRegisterResponse generateTokens(UserEntity user, String ip, HttpHeaders headers) throws Exception;
    public VerifyRegisterResponse login(LoginDto body, String ip, HttpHeaders headers) throws Exception;
    public VerifyRegisterResponse refreshToken(RefreshRequest refreshRequest) throws Exception;
}
