package com.example.task_manager_app.planning.table.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class TableStatisticsResponse {
    private Long id;
    private Short score;
    private Instant createdAt;
}
