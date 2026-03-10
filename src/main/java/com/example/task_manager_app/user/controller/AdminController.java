package com.example.task_manager_app.user.controller;

import com.example.task_manager_app.user.dto.ProfileResponseDTO;
import com.example.task_manager_app.user.interfaces.AdminService;
import com.example.task_manager_app.common.dto.FilterRequestDTO;
import com.example.task_manager_app.user.dto.AdminRequestDTO;
import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.common.validation.ValidationGroups;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/admins")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping
    @PreAuthorize("@authManager.hasPermission(authentication, 'ADMIN_READ')")
    public ResponseEntity<CustomPageResponse<ProfileResponseDTO>> getAdmins(FilterRequestDTO param, Pageable pageable) {
        return ResponseEntity.ok(adminService.getAdmins(param, pageable));
    }

    @PostMapping
    @PreAuthorize("@authManager.hasPermission(authentication, 'ADMIN_CREATE')")
    public ResponseEntity<Void> addAdmin(@Validated(ValidationGroups.Create.class) @RequestBody AdminRequestDTO body) throws Exception {
        this.adminService.addAdmin(body);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adminId}")
    @PreAuthorize("@authManager.hasPermission(authentication, 'ADMIN_UPDATE')")
    public ResponseEntity<ProfileResponseDTO> updateAdmin(@PathVariable long adminId, @RequestBody @Valid AdminRequestDTO body) throws Exception {
        ProfileResponseDTO res = this.adminService.updateAdmin(adminId, body);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{adminId}")
    @PreAuthorize("@authManager.hasPermission(authentication, 'ADMIN_DELETE')")
    public ResponseEntity<Void> deleteAdmin(@PathVariable long adminId) throws Exception {
        this.adminService.deleteAdmin(adminId);
        return ResponseEntity.ok().build();
    }
}
