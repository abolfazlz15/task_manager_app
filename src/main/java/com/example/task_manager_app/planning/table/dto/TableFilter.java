package com.example.task_manager_app.planning.table.dto;
import org.springframework.data.domain.Sort;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class TableFilter {
    private String name;

    private Long userId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdAfter;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdBefore;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedAfter;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedBefore;

    private Integer page = 0;

    private Integer pageSize = 20;

    private String sortBy = "createdAt";

    private Sort.Direction sortDirection = Sort.Direction.DESC;
}
