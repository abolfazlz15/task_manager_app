package com.example.task_manager_app.auth.controller;

import java.security.Principal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import com.example.task_manager_app.auth.interfaces.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest body) {
       authService.register(body);
       return ResponseEntity.ok().build();
    }
    @PostMapping("/register/verify")
    public ResponseEntity<VerifyRegisterResponse> verifyRegister(@RequestBody @Valid VerifyRegisterRequest body, @RequestHeader HttpHeaders headers, HttpServletRequest req) throws Exception {
       return ResponseEntity.ok(authService.verifyRegister(body, req.getRemoteAddr(), headers));
    }
    @PostMapping("/login")
    public ResponseEntity<VerifyRegisterResponse> login(@RequestBody @Valid LoginDto body, @RequestHeader HttpHeaders headers, HttpServletRequest req) throws Exception {
        return ResponseEntity.ok(authService.login(body, req.getRemoteAddr(), headers));
    }
    @PostMapping("/refresh")
    public ResponseEntity<VerifyRegisterResponse> refreshToken(@RequestBody @Valid RefreshRequest body) throws Exception {
        return ResponseEntity.ok(authService.refreshToken(body));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Principal principal, @RequestBody @Valid LogoutRequest body) throws Exception {
        authService.logout(principal, body);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot-password/send/otp")
    public ResponseEntity<Void> forgetPasswordSendOtp(@RequestBody @Valid ForgetPasswordSendOtpRequest body) {
        body.validate();
        authService.forgetPasswordSendOtp(body);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forgot-password/verify/otp")
    public ResponseEntity<ForgetPasswordVerifyOtpResponse> forgetPasswordVerifyOtp(@RequestBody @Valid ForgetPasswordVerifyOtpRequest body) {
        body.validate();
        return ResponseEntity.ok(authService.forgetPasswordVerifyOtp(body));
    }

    @PatchMapping("/forgot-password/reset")
    public ResponseEntity<Void> forgetPasswordReset(@RequestBody @Valid ForgetPasswordRequest body) throws Exception {
        body.validate();
        authService.forgetPassword(body);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/admins/login")
    public ResponseEntity<VerifyRegisterResponse> loginAdmin(@RequestBody @Valid LoginDto body, @RequestHeader HttpHeaders headers, HttpServletRequest req) throws Exception {
        VerifyRegisterResponse res = this.authService.loginAdmin(body, req.getRemoteAddr(), headers);
        return ResponseEntity.ok(res);
    }
}
