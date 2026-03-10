package com.example.task_manager_app.planning.table.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.example.task_manager_app.planning.table.validation.TableValidationGroups;

@Getter
@Setter
@AllArgsConstructor
public class TableRequestDTO {
    @NotBlank
    @Size(max=150)
    private String name;
    private String description;

    @NotNull(groups = TableValidationGroups.Admin.class)
    private Long userId;
}
