package com.example.task_manager_app.auth.interfaces;

import java.security.Principal;

import org.springframework.http.HttpHeaders;

import com.example.task_manager_app.auth.dto.LoginDto;
import com.example.task_manager_app.auth.dto.LogoutRequest;
import com.example.task_manager_app.auth.dto.RefreshRequest;
import com.example.task_manager_app.auth.dto.RegisterRequest;
import com.example.task_manager_app.auth.dto.VerifyRegisterRequest;
import com.example.task_manager_app.auth.dto.VerifyRegisterResponse;
import com.example.task_manager_app.user.entity.UserEntity;

public interface AuthService {
    public void register(RegisterRequest registerRequest);
    public VerifyRegisterResponse verifyRegister(VerifyRegisterRequest verifyRegisterRequest, String ip, HttpHeaders headers) throws Exception;
    public VerifyRegisterResponse generateTokens(UserEntity user, String ip, HttpHeaders headers) throws Exception;
    public VerifyRegisterResponse login(LoginDto body, String ip, HttpHeaders headers) throws Exception;
    public VerifyRegisterResponse refreshToken(RefreshRequest refreshRequest) throws Exception;
    public void logout(Principal principal, LogoutRequest body) throws Exception;
}
