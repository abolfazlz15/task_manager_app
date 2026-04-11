package com.example.task_manager_app.planning.table.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TableWeeklyTemplateDto {
    List<String> names;
    List<String> times;
    List<String>  duration;
    List<Boolean> saturday;
    List<Boolean> sunday;
    List<Boolean> monday;
    List<Boolean> tuesday;
    List<Boolean> wednesday;
    List<Boolean> thursday;
    List<Boolean> friday;
}
