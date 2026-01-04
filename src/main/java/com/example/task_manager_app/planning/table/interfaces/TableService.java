package com.example.task_manager_app.planning.table.interfaces;

import com.example.task_manager_app.planning.table.dto.CreateTableRequest;
import com.example.task_manager_app.planning.table.dto.TableResponse;
import com.example.task_manager_app.planning.table.dto.UpdateTableDetailRequest;
import com.example.task_manager_app.planning.table.dto.UpdateTableRequest;

import java.security.Principal;

public interface TableService {
    public TableResponse create(CreateTableRequest tableData, Principal principal);
    public void updateDetail(Long tableId, UpdateTableDetailRequest tableDetailData, Principal principal);
    public TableResponse update(Long tableId, UpdateTableRequest tableData, Principal principal);
}
