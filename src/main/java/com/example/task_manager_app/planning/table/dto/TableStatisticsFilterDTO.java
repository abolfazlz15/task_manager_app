package com.example.task_manager_app.planning.table.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
public class TableStatisticsFilterDTO {
    Long userId;
    
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant from;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Instant to;

    @AssertTrue
    public boolean isFromBeforeTo() {
        if (from == null || to == null) {
            return false;
        }

        return from.isBefore(to);
    }
}
