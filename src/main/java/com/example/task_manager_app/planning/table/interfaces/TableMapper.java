package com.example.task_manager_app.planning.table.interfaces;

import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.planning.table.dto.TableDetailResponseDTO;
import com.example.task_manager_app.planning.table.dto.TableResponseDTO;
import com.example.task_manager_app.planning.table.dto.TableDetailRequestDTO;
import com.example.task_manager_app.planning.table.dto.TableStatisticsResponseDTO;
import com.example.task_manager_app.planning.table.entity.ColumnEntity;
import com.example.task_manager_app.planning.table.entity.RowEntity;
import com.example.task_manager_app.planning.table.entity.TableEntity;
import com.example.task_manager_app.planning.table.entity.TableStatisticEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface TableMapper {
    void updateEntityFromDto(TableDetailRequestDTO dto, @MappingTarget TableEntity entity);
    ColumnEntity toEntity(TableDetailRequestDTO.ColumnRequest dto);
    RowEntity toEntity(TableDetailRequestDTO.RowRequest dto);
    CustomPageResponse<TableResponseDTO> toTableResponseDTOPagination(CustomPageResponse<TableEntity> entity);

    @Mapping(target = "userId", source = "user.id")
    TableResponseDTO toTableResponseDTO(TableEntity entity);

    @Mapping(target = "columns", source = "columns")
    TableDetailResponseDTO toTableDetailResponseDTO(TableEntity entity);

    @Mapping(target = "rows", source = "rows")
    TableDetailResponseDTO.ColumnRequest toColumnRequest(ColumnEntity entity);
    TableDetailResponseDTO.RowRequest toRowRequest(RowEntity entity);

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

    @Mapping(source = "statistic.monthlyScore", target = "monthlyScore")
    @Mapping(source = "statistic.seasonalScore", target = "seasonalScore")
    @Mapping(source = "statistic.annualScore", target = "annualScore")
    @Mapping(source = "statistic.overallScore", target = "overallScore")
    TableStatisticsResponseDTO toTableStatisticsResponseDTO(TableStatisticEntity statistic, CustomPageResponse<TableStatisticEntity> weeklyScores);

    @Mapping(source = "content", target = "content")
    CustomPageResponse<TableStatisticsResponseDTO.WeeklyScoreResponse> toWeeklyScoreResponse(CustomPageResponse<TableStatisticEntity> entity);
}
