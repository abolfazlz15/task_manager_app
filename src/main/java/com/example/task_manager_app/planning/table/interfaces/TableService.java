package com.example.task_manager_app.planning.table.interfaces;

import com.example.task_manager_app.planning.table.dto.*;

import java.security.Principal;

public interface TableService {
    public TableResponse create(CreateTableRequest tableData, Principal principal);
    public void updateDetail(Long tableId, UpdateTableDetailRequest tableDetailData, Principal principal);
    public TableResponse update(Long tableId, UpdateTableRequest tableData, Principal principal);
    public TableDetailResponse detail(Long tableId, Principal principal);
}
