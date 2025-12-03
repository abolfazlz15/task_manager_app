package com.example.task_manager_app.user.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.interfaces.UserService;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("profile")
    public ResponseEntity<UserEntity> getProfile(Principal principal) {
        return ResponseEntity.ok(userService.getProfile(principal));
    }
}
