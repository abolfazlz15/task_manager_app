package com.example.task_manager_app.planning.table.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateTableRequest {
    private Long userId = null;
    @NotBlank
    @Size(max=150)
    private String name;
    private String description;
}
