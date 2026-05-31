package com.example.task_manager_app.user.controller;

import com.example.task_manager_app.user.dto.ProfileResponseDTO;
import com.example.task_manager_app.user.interfaces.AdminUserService;
import com.example.task_manager_app.common.dto.FilterRequestDTO;
import com.example.task_manager_app.user.dto.AdminRequestDTO;
import com.example.task_manager_app.common.response.CustomPageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {
    private final AdminUserService adminUserService;

    @GetMapping
    @PreAuthorize("@authManager.hasPermission(authentication, 'USER_READ')")
    public ResponseEntity<CustomPageResponse<ProfileResponseDTO>> getUsers(FilterRequestDTO param, Pageable pageable) {
        return ResponseEntity.ok(adminUserService.getUsers(param, pageable));
    }

    @PatchMapping("/{userId}")
    @PreAuthorize("@authManager.hasPermission(authentication, 'USER_UPDATE')")
    public ResponseEntity<ProfileResponseDTO> updateUser(@PathVariable long userId, @RequestBody @Valid AdminRequestDTO body) throws Exception {
        ProfileResponseDTO res = this.adminUserService.updateUser(userId, body);
        return ResponseEntity.ok(res);
    }
}
