package com.example.task_manager_app.admin.role.dto;

import com.example.task_manager_app.auth.enums.Permissions;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AddRoleRequest {
    @NotBlank
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Set<Permissions> permissions;
}
