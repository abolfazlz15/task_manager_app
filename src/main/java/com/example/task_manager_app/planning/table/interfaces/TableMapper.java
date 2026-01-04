package com.example.task_manager_app.planning.table.interfaces;

import com.example.task_manager_app.planning.table.dto.UpdateTableDetailRequest;
import com.example.task_manager_app.planning.table.entity.ColumnEntity;
import com.example.task_manager_app.planning.table.entity.RowEntity;
import com.example.task_manager_app.planning.table.entity.TableEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TableMapper {

    void updateEntityFromDto(UpdateTableDetailRequest dto, @MappingTarget TableEntity entity);

    ColumnEntity toEntity(UpdateTableDetailRequest.ColumnRequest dto);
    RowEntity toEntity(UpdateTableDetailRequest.RowRequest dto);

    @AfterMapping
    default void linkRelations(@MappingTarget TableEntity table) {
        if (table.getColumns() != null) {
            table.getColumns().forEach(column -> {
                column.setTable(table);
                if (column.getRows() != null) {
                    column.getRows().forEach(row -> row.setColumn(column));
                }
            });
        }
    }
}