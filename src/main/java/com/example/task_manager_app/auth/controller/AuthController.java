package com.example.task_manager_app.auth.controller;

import com.example.task_manager_app.auth.dto.*;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_manager_app.auth.interfaces.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.security.Principal;


@RestController
@RequestMapping("/auth")
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

    @PostMapping("/login")
    public VerifyRegisterResponse login(@RequestBody @Valid LoginDto body, @RequestHeader HttpHeaders headers, HttpServletRequest req) throws Exception {
        return authService.login(body, req.getRemoteAddr(), headers);
    }

    @PostMapping("/logout")
    public void logout(Principal principal, @RequestBody @Valid LogoutRequest body) throws Exception {
        authService.logout(principal, body);
    }
}
