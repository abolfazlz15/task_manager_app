package com.example.task_manager_app.auth.controller;

import com.example.task_manager_app.auth.dto.LoginRequestDTO;
import com.example.task_manager_app.auth.dto.VerifyRegisterResponseDTO;
import com.example.task_manager_app.auth.interfaces.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/auth")
@RequiredArgsConstructor
public class AdminAuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<VerifyRegisterResponseDTO> loginAdmin(@RequestBody @Valid LoginRequestDTO body, @RequestHeader HttpHeaders headers, HttpServletRequest req) throws Exception {
        VerifyRegisterResponseDTO res = this.authService.loginAdmin(body, req.getRemoteAddr(), headers);
        return ResponseEntity.ok(res);
    }
}
