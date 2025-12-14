package com.example.task_manager_app.planning.table.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class TableResponse {
    private Long id;
    private String name;
    private String description;
    private Instant createdAt;
}
