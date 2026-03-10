package com.example.task_manager_app.auth.controller;

import java.security.Principal;

import com.example.task_manager_app.auth.dto.*;
import com.example.task_manager_app.common.validation.ValidationGroups;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_manager_app.auth.interfaces.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequestDTO body) {
       authService.register(body);
       return ResponseEntity.ok().build();
    }
    @PostMapping("/register/verify")
    public ResponseEntity<VerifyRegisterResponseDTO> verifyRegister(@RequestBody @Valid VerifyRegisterRequestDTO body, @RequestHeader HttpHeaders headers, HttpServletRequest req) throws Exception {
       return ResponseEntity.ok(authService.verifyRegister(body, req.getRemoteAddr(), headers));
    }
    @PostMapping("/login")
    public ResponseEntity<VerifyRegisterResponseDTO> login(@RequestBody @Valid LoginRequestDTO body, @RequestHeader HttpHeaders headers, HttpServletRequest req) throws Exception {
        return ResponseEntity.ok(authService.login(body, req.getRemoteAddr(), headers));
    }
    @PostMapping("/refresh")
    public ResponseEntity<VerifyRegisterResponseDTO> refreshToken(@RequestBody @Valid RefreshTokenRequestDTO body) throws Exception {
        return ResponseEntity.ok(authService.refreshToken(body));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Principal principal, @Validated(ValidationGroups.Update.class) @RequestBody RefreshTokenRequestDTO body) throws Exception {
        authService.logout(principal, body);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forget-password/send/otp")
    public ResponseEntity<Void> forgetPasswordSendOtp(@RequestBody @Valid ForgetPasswordSendOtpRequestDTO body) {
        body.validate();
        authService.forgetPasswordSendOtp(body);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/forget-password/verify/otp")
    public ResponseEntity<ForgetPasswordVerifyOtpResponseDTO> forgetPasswordVerifyOtp(@RequestBody @Valid ForgetPasswordVerifyOtpRequestDTO body) {
        body.validate();
        return ResponseEntity.ok(authService.forgetPasswordVerifyOtp(body));
    }

    @PatchMapping("/forget-password/reset")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid ResetPasswordRequestDTO body) throws Exception {
        body.validate();
        authService.resetPassword(body);
        return ResponseEntity.ok().build();
    }
}
