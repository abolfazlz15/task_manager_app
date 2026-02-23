package com.example.task_manager_app.planning.table.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TableResponse {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private Instant createdAt;
}
