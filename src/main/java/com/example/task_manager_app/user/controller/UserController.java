package com.example.task_manager_app.user.controller;

import java.io.IOException;
import java.security.Principal;

import com.example.task_manager_app.user.dto.ProfileResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.task_manager_app.user.dto.UserRequestDTO;
import com.example.task_manager_app.user.interfaces.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDTO> getProfile(Principal principal) {
        return ResponseEntity.ok(userService.getProfile(principal));
    }

    @PatchMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProfileResponseDTO> updateUser(Principal principal, @ModelAttribute @Valid UserRequestDTO body) throws IOException {
        return ResponseEntity.ok(userService.updateUser(principal, body));
    }
}
