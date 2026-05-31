package com.example.task_manager_app.planning.table.dto;

import com.example.task_manager_app.common.constant.IConstant;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class TableStatisticsFilterDTO {
    private Long userId;
    
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant from;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant to;

    private Integer page = 0;

    private Integer pageSize = IConstant.DEFAULT_PAGE_SIZE;

    private String sortBy = "createdAt";

    private Sort.Direction sortDirection = Sort.Direction.DESC;
}
