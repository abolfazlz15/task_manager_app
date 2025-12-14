package com.example.task_manager_app.planning.table.interfaces;

import com.example.task_manager_app.planning.table.dto.CreateTableRequest;
import com.example.task_manager_app.planning.table.dto.TableResponse;
import com.example.task_manager_app.planning.table.entity.TableEntity;

import java.security.Principal;

public interface TableService {
    public TableResponse create(CreateTableRequest tableData, Principal principal);
}
