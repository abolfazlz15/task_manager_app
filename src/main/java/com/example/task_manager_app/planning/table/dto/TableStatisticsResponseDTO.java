package com.example.task_manager_app.planning.table.dto;

import com.example.task_manager_app.common.response.CustomPageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class TableStatisticsResponseDTO {
    private Short monthlyScore;
    private Short seasonalScore;
    private Short annualScore;
    private Short overallScore;
    private CustomPageResponse<WeeklyScoreResponse> weeklyScores;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class WeeklyScoreResponse {
        private Long id;
        private Short score;
        private Instant createdAt;
    }
}