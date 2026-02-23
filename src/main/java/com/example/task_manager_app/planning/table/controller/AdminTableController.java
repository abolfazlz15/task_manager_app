package com.example.task_manager_app.planning.table.controller;
import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.planning.table.dto.*;
import com.example.task_manager_app.planning.table.interfaces.TableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("admin/table")
@RequiredArgsConstructor
public class AdminTableController {
    private final TableService tableService;

    @GetMapping("/{tableId}")
//    @PreAuthorize("@authManager.hasPermission(authentication, 'TABLE_READ')")
    public ResponseEntity<TableDetailResponse> getTable(@PathVariable Long tableId, Principal principal) {
        TableDetailResponse table = tableService.getTable(tableId, principal);
        return ResponseEntity.status(200).body(table);
    }

    @GetMapping
//    @PreAuthorize("@authManager.hasPermission(authentication, 'TABLE_READ')")
    public ResponseEntity<CustomPageResponse<TableResponse>> getAllTables(TableFilter filter, Principal principal) {
        CustomPageResponse<TableResponse> table = tableService.getAllTable(filter, principal, true);
        return ResponseEntity.status(200).body(table);
    }

    @DeleteMapping("/{tableId}")
//    @PreAuthorize("@authManager.hasPermission(authentication, 'TABLE_DELETE')")
    public ResponseEntity<Void> deleteTable(@PathVariable("tableId") Long tableId) {
        tableService.delete(tableId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{tableId}")
//    @PreAuthorize("@authManager.hasPermission(authentication, 'TABLE_UPDATE')")
    public ResponseEntity<TableResponse> updateTable(@PathVariable Long tableId, @RequestBody @Valid UpdateTableRequest body, Principal principal) {
        TableResponse table = tableService.update(tableId, body, principal);
        return ResponseEntity.status(200).body(table);
    }

    @PutMapping("/{tableId}/detail/")
//    @PreAuthorize("@authManager.hasPermission(authentication, 'TABLE_READ')")
    public ResponseEntity<Void> updateDetailTable(@PathVariable Long tableId, @RequestBody @Valid UpdateTableDetailRequest body, Principal principal) {
        tableService.updateDetail(tableId, body, principal);
        return ResponseEntity.ok().build();
    }

    @PostMapping
//    @PreAuthorize("@authManager.hasPermission(authentication, 'TABLE_CREATE')")
    public ResponseEntity<TableResponse> createTable(@RequestBody @Valid CreateTableRequest body, Principal principal) {
        TableResponse table = tableService.create(body, principal);
        return ResponseEntity.status(201).body(table);
    }

}
