package com.example.task_manager_app.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_manager_app.auth.dto.RegisterRequest;
import com.example.task_manager_app.auth.dto.VerifyRegisterRequest;
import com.example.task_manager_app.auth.dto.VerifyRegisterResponse;
import com.example.task_manager_app.auth.interfaces.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;



@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequest body) {
       authService.register(body);
    }
    @PostMapping("/verify")
    public VerifyRegisterResponse verifyRegister(@RequestBody @Valid VerifyRegisterRequest body, @RequestHeader HttpHeaders headers, HttpServletRequest req) throws Exception {
       return authService.verifyRegister(body, req.getRemoteAddr(), headers);
    }
}
    @PostMapping("login")
    public void login(@RequestBody @Valid LoginDto body, @RequestHeader HttpHeaders headers, HttpServletRequest req) {
        authService.login(body, req.getRemoteAddr(), headers);
    }
}
