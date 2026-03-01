package com.example.task_manager_app.admin.user.controller;

import com.example.task_manager_app.admin.user.interfaces.AdminUserService;
import com.example.task_manager_app.common.dto.FilterRequest;
import com.example.task_manager_app.common.dto.ProfileRequest;
import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.user.entity.UserEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    private final AdminUserService adminUserService;

    @Autowired
    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping
   @PreAuthorize("@authManager.hasPermission(authentication, 'USER_READ')")
    public ResponseEntity<CustomPageResponse<UserEntity>> getUsers(FilterRequest param, Pageable pageable) {
        return ResponseEntity.ok(adminUserService.getUsers(param, pageable));
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("@authManager.hasPermission(authentication, 'USER_UPDATE')")
    public ResponseEntity<UserEntity> updateUser(@PathVariable long userId, @RequestBody @Valid ProfileRequest body) throws Exception {
        UserEntity res = this.adminUserService.updateUser(userId, body);
        return ResponseEntity.ok(res);
    }
}
