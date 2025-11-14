package com.example.task_manager_app.auth.interfaces;

import com.example.task_manager_app.auth.dto.LoginDto;
import org.springframework.http.HttpHeaders;

public interface AuthService {
    void login(LoginDto body, String ip, HttpHeaders headers);
}
