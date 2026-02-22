package com.example.task_manager_app.admin.admin.controller;

import com.example.task_manager_app.admin.admin.dto.AddAdminRequest;
import com.example.task_manager_app.admin.admin.interfaces.AdminService;
import com.example.task_manager_app.common.dto.FilterRequest;
import com.example.task_manager_app.common.dto.ProfileRequest;
import com.example.task_manager_app.user.entity.UserEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admins")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    @PreAuthorize("@authManager.hasPermission(authentication, 'ADMIN_READ')")
    public ResponseEntity<Page<UserEntity>> getAdmins(FilterRequest param, Pageable pageable) {
        return ResponseEntity.ok(adminService.getAdmins(param, pageable));
    }

    @PostMapping
    @PreAuthorize("@authManager.hasPermission(authentication, 'ADMIN_CREATE')")
    public ResponseEntity<Void> addAdmin(@RequestBody @Valid AddAdminRequest body) throws Exception {
        this.adminService.addAdmin(body);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adminId}")
    @PreAuthorize("@authManager.hasPermission(authentication, 'ADMIN_UPDATE')")
    public ResponseEntity<UserEntity> updateAdmin(@PathVariable long adminId, @RequestBody @Valid ProfileRequest body) throws Exception {
        UserEntity res = this.adminService.updateAdmin(adminId, body);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{adminId}")
    @PreAuthorize("@authManager.hasPermission(authentication, 'ADMIN_DELETE')")
    public ResponseEntity<Void> removeAdmin(@PathVariable long adminId) throws Exception {
        this.adminService.removeAdmin(adminId);
        return ResponseEntity.ok().build();
    }
}
