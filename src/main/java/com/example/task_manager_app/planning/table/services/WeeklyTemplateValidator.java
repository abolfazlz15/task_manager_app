package com.example.task_manager_app.planning.table.services;

import com.example.task_manager_app.common.exception.BadRequestException;
import com.example.task_manager_app.planning.table.dto.TableWeeklyTemplateDto;
import com.example.task_manager_app.planning.table.enums.TemplateTypeEnum;
import com.example.task_manager_app.planning.table.interfaces.TemplateValidator;
import org.springframework.stereotype.Component;

@Component
public class WeeklyTemplateValidator implements TemplateValidator<TableWeeklyTemplateDto> {

    @Override
    public void validate(TableWeeklyTemplateDto dto) {
//    it not complete

        if (dto.getNames() == null || dto.getNames().isEmpty())
            throw new BadRequestException("Names cannot be empty");

        if (dto.getTimes().size() != dto.getNames().size())
            throw new BadRequestException("Times size does not match names size");

        if (dto.getDuration().size() != dto.getNames().size())
            throw new BadRequestException("Times size does not match names size");

        if (dto.getSaturday().size() != dto.getNames().size())
            throw new BadRequestException("Saturday row count mismatch");
        if (dto.getSunday().size() != dto.getNames().size())
            throw new BadRequestException("Sunday row count mismatch");
        if (dto.getMonday().size() != dto.getNames().size())
            throw new BadRequestException("Monday row count mismatch");
        if (dto.getTuesday().size() != dto.getNames().size())
            throw new BadRequestException("Tuesday row count mismatch");
        if (dto.getWednesday().size() != dto.getNames().size())
            throw new BadRequestException("Wednesday row count mismatch");
        if (dto.getThursday().size() != dto.getNames().size())
            throw new BadRequestException("Thursday row count mismatch");
        if (dto.getFriday().size() != dto.getNames().size())
            throw new BadRequestException("Friday row count mismatch");
    }

    @Override
    public TemplateTypeEnum supports() {
        return TemplateTypeEnum.WEEKLY;
    }
}