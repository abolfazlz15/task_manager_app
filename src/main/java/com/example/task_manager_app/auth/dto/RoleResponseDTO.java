package com.example.task_manager_app.auth.dto;

import com.example.task_manager_app.auth.enums.Permissions;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleResponseDTO {
    private Long id;
    private String name;
    private Set<Permissions> permissions;
}
