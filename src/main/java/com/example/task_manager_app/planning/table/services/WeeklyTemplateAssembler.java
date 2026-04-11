package com.example.task_manager_app.planning.table.services;

import com.example.task_manager_app.planning.table.dto.TableWeeklyTemplateDto;
import com.example.task_manager_app.planning.table.entity.ColumnEntity;
import com.example.task_manager_app.planning.table.entity.RowEntity;
import com.example.task_manager_app.planning.table.entity.TableEntity;
import com.example.task_manager_app.planning.table.enums.DataTypeEnum;
import com.example.task_manager_app.planning.table.enums.TemplateTypeEnum;
import com.example.task_manager_app.planning.table.interfaces.TemplateAssembler;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WeeklyTemplateAssembler implements TemplateAssembler<TableWeeklyTemplateDto> {
    private static final Map<String, DataTypeEnum> ROW_DATA_TYPE = new HashMap<>(
            Map.of(
                "names", DataTypeEnum.TEXT,
                "times", DataTypeEnum.TEXT,
                "duration", DataTypeEnum.TEXT,
                "saturday", DataTypeEnum.BOOLEAN,
                "sunday", DataTypeEnum.BOOLEAN,
                "monday", DataTypeEnum.BOOLEAN,
                "tuesday", DataTypeEnum.BOOLEAN,
                "wednesday", DataTypeEnum.BOOLEAN,
                "thursday", DataTypeEnum.BOOLEAN,
                "friday", DataTypeEnum.BOOLEAN
            )
    );
    @Override
    public TemplateTypeEnum supports() {
        return TemplateTypeEnum.WEEKLY;
    }

    @Override
    public TableEntity assembleTable(TableEntity table, TableWeeklyTemplateDto dto) {

        Set<ColumnEntity> columns = new LinkedHashSet<>();
        int colIndex = 0;

        columns.add(createColumn(table, "names", dto.getNames(), colIndex++));
        columns.add(createColumn(table, "times", dto.getTimes(), colIndex++));
        columns.add(createColumn(table, "duration", dto.getDuration(), colIndex++));
        columns.add(createColumn(table, "saturday", toStringList(dto.getSaturday()), colIndex++));
        columns.add(createColumn(table, "sunday", toStringList(dto.getSunday()), colIndex++));
        columns.add(createColumn(table, "monday", toStringList(dto.getMonday()), colIndex++));
        columns.add(createColumn(table, "tuesday", toStringList(dto.getTuesday()), colIndex++));
        columns.add(createColumn(table, "wednesday", toStringList(dto.getWednesday()), colIndex++));
        columns.add(createColumn(table, "thursday", toStringList(dto.getThursday()), colIndex++));
        columns.add(createColumn(table, "friday", toStringList(dto.getFriday()), colIndex++));

        table.setColumns(columns);

        return table;
    }

    private ColumnEntity createColumn(TableEntity table, String name, List<String> values, int index) {
        ColumnEntity column = new ColumnEntity();
        column.setTable(table);
        column.setName(name);
        column.setIndex(index);

        List<RowEntity> rows = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            RowEntity row = new RowEntity();
            row.setColumn(column);
            row.setIndex(i);
            row.setValue(values.get(i));
            row.setDataType(ROW_DATA_TYPE.get(column.getName()));
            rows.add(row);
        }

        column.setRows(rows);
        return column;
    }

    private List<String> toStringList(List<Boolean> list) {
        return list.stream().map(String::valueOf).toList();
    }
}
