package com.example.task_manager_app.planning.table.dto;

import com.example.task_manager_app.planning.table.enums.DataTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UpdateTableDetailRequest {
    private Long userId = null;

    @NotBlank(message = "Table name is required")
    private String tableName;

    private List<ColumnRequest> columns;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ColumnRequest {
        @NotBlank(message = "Column name is required")
        private String name;

        @NotNull(message = "Column index is required")
        private Integer index;

        @NotEmpty(message = "At least one row is required")
        private List<RowRequest> rows;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class RowRequest {
        @NotNull(message = "Row index is required")
        private Integer index;

        @NotBlank(message = "Row value is required")
        private String value;

        @NotNull(message = "Data type is required")
        private DataTypeEnum dataType;
    }
}