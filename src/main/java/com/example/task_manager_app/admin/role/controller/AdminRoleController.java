package com.example.task_manager_app.admin.role.controller;

import com.example.task_manager_app.admin.role.interfaces.AdminRoleService;
import com.example.task_manager_app.auth.entity.RoleEntity;
import com.example.task_manager_app.admin.role.dto.AddRoleRequest;
import com.example.task_manager_app.user.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/roles")
public class AdminRoleController {
    private final AdminRoleService adminRoleService;

    @Autowired
    public AdminRoleController(AdminRoleService adminRoleService) {
        this.adminRoleService = adminRoleService;
    }

    @GetMapping
    @PreAuthorize("@authManager.hasPermission(authentication, 'SUPERUSER')")
    public ResponseEntity<List<RoleEntity>> getRoles() {
        return ResponseEntity.ok(adminRoleService.getRoles());
    }

    @PostMapping
    @PreAuthorize("@authManager.hasPermission(authentication, 'SUPERUSER')")
    public ResponseEntity<Void> addRole(@RequestBody @Valid AddRoleRequest body) {
        this.adminRoleService.addRole(body);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{roleId}")
    @PreAuthorize("@authManager.hasPermission(authentication, 'SUPERUSER')")
    public ResponseEntity<RoleEntity> updateRole(@PathVariable long roleId, @RequestBody @Valid AddRoleRequest body) {
        return ResponseEntity.ok(this.adminRoleService.updateRole(roleId, body));
    }

    @DeleteMapping("/{roleId}")
    @PreAuthorize("@authManager.hasPermission(authentication, 'SUPERUSER')")
    public ResponseEntity<Void> removeRole(@PathVariable long roleId) {
        this.adminRoleService.removeRole(roleId);
        return ResponseEntity.ok().build();
    }
}
