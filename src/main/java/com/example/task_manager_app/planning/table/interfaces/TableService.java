package com.example.task_manager_app.planning.table.interfaces;

import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.planning.table.dto.*;

import java.security.Principal;
import java.util.List;

public interface TableService {
    public TableResponse create(CreateTableRequest tableData, Principal principal);
    public void updateDetail(Long tableId, UpdateTableDetailRequest tableDetailData, Principal principal);
    public TableResponse update(Long tableId, UpdateTableRequest tableData, Principal principal);
    public TableDetailResponse getTable(Long tableId, Long userId, Principal principal);
    public CustomPageResponse<TableResponse> getAllTable(TableFilter filter, Principal principal, boolean isAdmin);
    public List<TableStatisticsResponse> getTableStatistics(Long tableId, GetTableStatisticsRequest queryParam, boolean isAdmin, Principal principal);
    public void delete(Long tableId);
}
