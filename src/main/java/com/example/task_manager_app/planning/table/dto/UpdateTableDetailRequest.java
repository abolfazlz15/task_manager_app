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
    @NotEmpty
    private List<ColumnRequest> columns;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ColumnRequest {
        @NotBlank
        private String name;

        @NotNull
        private Integer index;

        @NotEmpty
        private List<RowRequest> rows;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class RowRequest {
        @NotNull
        private Integer index;

        @NotBlank
        private String value;

        private DataTypeEnum dataType;
    }
}