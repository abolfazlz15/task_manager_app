package com.example.task_manager_app.planning.table.interfaces;

import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.planning.table.dto.*;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;

public interface TableService {
    public TableResponseDTO addTable(TableRequestDTO tableData, Principal principal);
    public void updateDetailTable(Long tableId, TableDetailRequestDTO tableDetailData, Principal principal);
    public TableResponseDTO updateTable(Long tableId, TableRequestDTO tableData, Principal principal);
    public TableDetailResponseDTO getTable(Long tableId, Long userId, Principal principal);
    public CustomPageResponse<TableResponseDTO> getTables(TableFilterDTO filter, Principal principal, boolean isAdmin);
    public TableStatisticsResponseDTO getTableStatistics(Long tableId, TableStatisticsFilterDTO queryParam, boolean isAdmin, Principal principal, Pageable pageable);
    public void deleteTable(Long tableId);
    public void addTableStatistic(long tableId, Principal principal);
}
