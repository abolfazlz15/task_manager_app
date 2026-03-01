package com.example.task_manager_app.planning.table.services;

import com.example.task_manager_app.common.exception.NotFoundException;
import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.planning.table.dto.*;
import com.example.task_manager_app.planning.table.entity.ColumnEntity;
import com.example.task_manager_app.planning.table.entity.TableEntity;
import com.example.task_manager_app.planning.table.entity.TableStatisticEntity;
import com.example.task_manager_app.planning.table.interfaces.TableMapper;
import com.example.task_manager_app.planning.table.interfaces.TableService;
import com.example.task_manager_app.planning.table.repository.ColumnRepository;
import com.example.task_manager_app.planning.table.repository.TableRepository;
import com.example.task_manager_app.planning.table.repository.TableSpecification;
import com.example.task_manager_app.planning.table.repository.TableStatisticRepository;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {
    private final TableRepository tableRepository;
    private final UserRepository userRepository;
    private final TableMapper tableMapper;
    private final ColumnRepository columnRepository;
    private final TableStatisticRepository tableStatisticRepository;

    @Override
    @Transactional
    public TableResponse create(CreateTableRequest tableData, Principal principal) {
        Long userId = tableData.getUserId() != null
                ? tableData.getUserId()
                : Long.parseLong(principal.getName());

        UserEntity userProxy = userRepository.getReferenceById(userId);
        TableEntity table = new TableEntity();
        table.setName(tableData.getName());
        table.setDescription(tableData.getDescription());
        table.setUser(userProxy);

        TableEntity tableSaved = tableRepository.save(table);
        return new TableResponse(
                tableSaved.getId(),
                tableSaved.getUser().getId(),
                tableSaved.getName(),
                tableSaved.getDescription(),
                tableSaved.getCreatedAt()
        );
    }

    @Override
    @Transactional
    public void updateDetail(Long tableId, UpdateTableDetailRequest tableDetailData, Principal principal) {
        Long userId = tableDetailData.getUserId() != null
                ? tableDetailData.getUserId()
                : Long.parseLong(principal.getName());
        TableEntity existingTable = tableRepository.findTableByUserIdAndTableId(tableId, userId)
                .orElseThrow(() -> new NotFoundException("Table Not Found"));

        columnRepository.deleteByTableId(tableId);
        existingTable.getColumns().clear();

        tableMapper.updateEntityFromDto(tableDetailData, existingTable);
        Double average = tableRepository.calculateAverage(existingTable.getId());
        short score = (average == null) ? 0 : (short) Math.round(average);
        existingTable.setScore(score);
        tableRepository.save(existingTable);

        TableStatisticEntity tableStatistic = new TableStatisticEntity();
        tableStatistic.setScore(score);
        tableStatistic.setTable(existingTable);

        tableStatisticRepository.save(tableStatistic);
    }

    @Override
    @Transactional
    public TableResponse update(Long tableId, UpdateTableRequest tableData, Principal principal) {
        Long userId = tableData.getUserId() != null
                ? tableData.getUserId()
                : Long.parseLong(principal.getName());
        TableEntity table = tableRepository.findTableByUserIdAndTableId(userId, tableId)
                .orElseThrow(() -> new NotFoundException("Table Not Found"));

        if (tableData.getDescription() != null) {
            table.setDescription(tableData.getDescription());
        }
        table.setName(tableData.getName());
        TableEntity saved = tableRepository.save(table);
        return new TableResponse(
                saved.getId(),
                saved.getUser().getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getCreatedAt()
        );
    }

    @Override
    public TableDetailResponse getTable(Long tableId, Long userId,Principal principal) {
        Long existsUserId = userId != null ? userId : Long.parseLong(principal.getName());
        TableEntity table = tableRepository.findTableWithColumnsAndRows(tableId, existsUserId)
                .orElseThrow(() -> new NotFoundException("Table Not Found"));

        List<TableDetailResponse.ColumnRequest> columns = table.getColumns()
                .stream()
                .map(this::mapColumn)
                .toList();

        return new TableDetailResponse(
                table.getId(),
                table.getName(),
                table.getDescription(),
                table.getScore(),
                table.getCreatedAt(),
                columns
        );
    }

    @Override
    public CustomPageResponse<TableResponse> getAllTable(TableFilter filter, Principal principal, boolean isAdmin) {
        Sort sort = Sort.by(filter.getSortDirection(), filter.getSortBy());
        Pageable pageable = PageRequest.of(
                filter.getPage(),
                filter.getPageSize(),
                sort
        );
        if (!isAdmin) {
            filter.setUserId(Long.parseLong(principal.getName()));
        }

        Specification<TableEntity> spec = TableSpecification.getTablesByFilter(filter);
        Page<TableEntity> tablePage = tableRepository.findAll(spec, pageable);
        Page<TableResponse> responsePage = tablePage.map(table -> TableResponse.builder()
                .id(table.getId())
                .userId(table.getUser().getId())
                .name(table.getName())
                .description(table.getDescription())
                .createdAt(table.getCreatedAt())
                .build()
        );
        return CustomPageResponse.fromPage(responsePage);
    }

    private TableDetailResponse.ColumnRequest mapColumn(ColumnEntity column) {
        List<TableDetailResponse.RowRequest> rows = column.getRows()
                .stream()
                .map(row -> new TableDetailResponse.RowRequest(
                        row.getId(),
                        row.getIndex(),
                        row.getValue(),
                        row.getDataType()
                ))
                .toList();

        return new TableDetailResponse.ColumnRequest(
                column.getId(),
                column.getIndex(),
                column.getName(),
                rows
        );
    }

    @Override
    public List<TableStatisticsResponse> getTableStatistics(Long tableId, GetTableStatisticsRequest queryParam, boolean isAdmin, Principal principal) {
        Long existsUserId = isAdmin && queryParam.getUserId() != null ? queryParam.getUserId() : Long.parseLong(principal.getName());
        TableEntity existingTable = tableRepository.findTableByUserIdAndTableId(tableId, existsUserId)
                .orElseThrow(() -> new NotFoundException("Table Not Found"));

        List<TableStatisticEntity> statistics = tableStatisticRepository.findByTableAndBetween(existingTable, queryParam.getFrom(), queryParam.getTo());

        return statistics.stream().map(item -> new TableStatisticsResponse(item.getId(), item.getScore(), item.getCreatedAt())).toList();
    }

    @Override
    @Transactional
    public void delete(Long tableId) {
        tableRepository.deleteById(tableId);
    }
}
