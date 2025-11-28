package com.example.task_manager_app.user.controller;

import java.io.IOException;
import java.security.Principal;

import com.example.task_manager_app.user.dto.UpdateProfileRequest;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserEntity> getProfile(Principal principal) {
        return ResponseEntity.ok(userService.getProfile(principal));
    }

    @PutMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserEntity> updateProfile(Principal principal, @ModelAttribute @Valid UpdateProfileRequest body) throws IOException {
        return ResponseEntity.ok(userService.updateProfile(principal, body));
    }
}
