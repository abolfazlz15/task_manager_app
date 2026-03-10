package com.example.task_manager_app.planning.table.services;

import com.example.task_manager_app.common.exception.NotFoundException;
import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.planning.table.dto.*;
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
    public TableResponseDTO addTable(TableRequestDTO tableData, Principal principal) {
        Long userId = tableData.getUserId() != null
                ? tableData.getUserId()
                : Long.parseLong(principal.getName());

        UserEntity userProxy = userRepository.getReferenceById(userId);
        TableEntity table = new TableEntity();
        table.setName(tableData.getName());
        table.setDescription(tableData.getDescription());
        table.setUser(userProxy);

        TableEntity tableSaved = tableRepository.save(table);
        return tableMapper.toTableResponseDTO(tableSaved);
    }

    @Override
    @Transactional
    public void updateDetailTable(Long tableId, TableDetailRequestDTO tableDetailData, Principal principal) {
        Long userId = tableDetailData.getUserId() != null
                ? tableDetailData.getUserId()
                : Long.parseLong(principal.getName());
        System.out.println(userId);
        System.out.println(tableId);
        TableEntity existingTable = tableRepository.findTableByUserIdAndTableId(userId, tableId)
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
    public TableResponseDTO updateTable(Long tableId, TableRequestDTO tableData, Principal principal) {
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
        return tableMapper.toTableResponseDTO(saved);
    }

    @Override
    public TableDetailResponseDTO getTable(Long tableId, Long userId, Principal principal) {
        Long existsUserId = userId != null ? userId : Long.parseLong(principal.getName());

        TableEntity table = tableRepository.findTableWithColumnsAndRows(existsUserId, tableId)
                .orElseThrow(() -> new NotFoundException("Table Not Found"));

        return tableMapper.toTableDetailResponseDTO(table);
    }

    @Override
    public CustomPageResponse<TableResponseDTO> getTables(TableFilterDTO filter, Principal principal, boolean isAdmin) {
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
        CustomPageResponse<TableEntity> tables = CustomPageResponse.fromPage(
                tableRepository.findAll(spec, pageable)
        );
        return tableMapper.toTableResponseDTOPagination(tables);
    }

    @Override
    public List<TableStatisticsResponseDTO> getTableStatistics(Long tableId, TableStatisticsFilterDTO queryParam, boolean isAdmin, Principal principal) {
        Long existsUserId = isAdmin && queryParam.getUserId() != null ? queryParam.getUserId() : Long.parseLong(principal.getName());
        TableEntity existingTable = tableRepository.findTableByUserIdAndTableId(existsUserId, tableId)
                .orElseThrow(() -> new NotFoundException("Table Not Found"));

        List<TableStatisticEntity> statistics = tableStatisticRepository.findByTableAndBetween(existingTable, queryParam.getFrom(), queryParam.getTo());

        return statistics.stream()
                .map(tableMapper::toTableStatisticsResponse)
                .toList();
    }

    @Override
    @Transactional
    public void deleteTable(Long tableId) {
        tableRepository.deleteById(tableId);
    }
}
