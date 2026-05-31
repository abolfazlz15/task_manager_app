package com.example.task_manager_app.auth.controller;

import com.example.task_manager_app.auth.dto.RoleResponseDTO;
import com.example.task_manager_app.auth.interfaces.AdminRoleService;
import com.example.task_manager_app.auth.dto.RoleRequestDTO;
import com.example.task_manager_app.common.validation.ValidationGroups;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/roles")
@RequiredArgsConstructor
public class AdminRoleController {
    private final AdminRoleService adminRoleService;

    @GetMapping
    @PreAuthorize("@authManager.hasPermission(authentication, 'SUPERUSER')")
    public ResponseEntity<List<RoleResponseDTO>> getRoles() {
        return ResponseEntity.ok(adminRoleService.getRoles());
    }

    @PostMapping
    @PreAuthorize("@authManager.hasPermission(authentication, 'SUPERUSER')")
    public ResponseEntity<Void> addRole(@Validated(ValidationGroups.Create.class) @RequestBody RoleRequestDTO body) {
        this.adminRoleService.addRole(body);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{roleId}")
    @PreAuthorize("@authManager.hasPermission(authentication, 'SUPERUSER')")
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable long roleId, @RequestBody @Valid RoleRequestDTO body) {
        return ResponseEntity.ok(this.adminRoleService.updateRole(roleId, body));
    }

    @DeleteMapping("/{roleId}")
    @PreAuthorize("@authManager.hasPermission(authentication, 'SUPERUSER')")
    public ResponseEntity<Void> deleteRole(@PathVariable long roleId) {
        this.adminRoleService.deleteRole(roleId);
        return ResponseEntity.ok().build();
    }
}
