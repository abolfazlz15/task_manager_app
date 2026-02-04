package com.example.task_manager_app.planning.table.dto;

import com.example.task_manager_app.planning.table.enums.DataTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TableDetailResponse {
    private Long id;
    private String name;
    private String description;
    private Short score;
    private Instant createdAt;
    private List<ColumnRequest> columns;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ColumnRequest {
        private Long id;
        private Integer index;
        private String name;
        private List<RowRequest> rows;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class RowRequest {
        private Long id;
        private Integer index;
        private String value;
        private DataTypeEnum dataType;
    }
}