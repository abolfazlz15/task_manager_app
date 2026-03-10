package com.example.task_manager_app.auth.dto;

import com.example.task_manager_app.auth.enums.Permissions;
import com.example.task_manager_app.common.validation.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleRequestDTO {
    @NotBlank(groups = ValidationGroups.Create.class)
    @NotNull(groups = ValidationGroups.Create.class)
    private String name;

    @NotNull(groups = ValidationGroups.Create.class)
    private Set<Permissions> permissions;
}
