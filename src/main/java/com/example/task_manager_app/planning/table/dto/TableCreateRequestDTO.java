package com.example.task_manager_app.planning.table.dto;

import com.example.task_manager_app.planning.table.enums.DataTypeEnum;
import com.example.task_manager_app.planning.table.validation.TableValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class TableCreateRequestDTO {
    @NotNull(groups = TableValidationGroups.Admin.class)
    private Long userId;
    private Long tableId;
    private Map<String, Object> payload;
}