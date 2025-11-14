package com.example.task_manager_app.auth.controller;

import com.example.task_manager_app.auth.dto.LoginDto;
import com.example.task_manager_app.auth.interfaces.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("login")
    public void login(@RequestBody @Valid LoginDto body, @RequestHeader HttpHeaders headers, HttpServletRequest req) {
        authService.login(body, req.getRemoteAddr(), headers);
    }
}
