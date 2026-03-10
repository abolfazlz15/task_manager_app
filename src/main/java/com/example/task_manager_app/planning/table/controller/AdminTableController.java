package com.example.task_manager_app.planning.table.controller;
import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.planning.table.dto.*;
import com.example.task_manager_app.planning.table.interfaces.TableService;
import com.example.task_manager_app.planning.table.validation.TableValidationGroups;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("admin/table")
@RequiredArgsConstructor
public class AdminTableController {
    private final TableService tableService;

    @GetMapping("/{tableId}/{userId}")
    @PreAuthorize("@authManager.hasPermission(authentication, 'TABLE_READ')")
    public ResponseEntity<TableDetailResponseDTO> getTable(@PathVariable Long tableId, @PathVariable Long userId, Principal principal) {
        TableDetailResponseDTO table = tableService.getTable(tableId, userId, principal);
        return ResponseEntity.status(200).body(table);
    }

    @GetMapping
    @PreAuthorize("@authManager.hasPermission(authentication, 'TABLE_READ')")
    public ResponseEntity<CustomPageResponse<TableResponseDTO>> getTables(TableFilterDTO filter, Principal principal) {
        CustomPageResponse<TableResponseDTO> table = tableService.getTables(filter, principal, true);
        return ResponseEntity.status(200).body(table);
    }

    @DeleteMapping("/{tableId}")
    @PreAuthorize("@authManager.hasPermission(authentication, 'TABLE_DELETE')")
    public ResponseEntity<Void> deleteTable(@PathVariable("tableId") Long tableId) {
        tableService.deleteTable(tableId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{tableId}")
    @PreAuthorize("@authManager.hasPermission(authentication, 'TABLE_UPDATE')")
    public ResponseEntity<TableResponseDTO> updateTable(@PathVariable Long tableId, @RequestBody @Valid TableRequestDTO body, Principal principal) {
        TableResponseDTO table = tableService.updateTable(tableId, body, principal);
        return ResponseEntity.status(200).body(table);
    }

    @PutMapping("/{tableId}/detail")
    @PreAuthorize("@authManager.hasPermission(authentication, 'TABLE_READ')")
    public ResponseEntity<Void> updateDetailTable(@PathVariable Long tableId, @Validated(TableValidationGroups.Admin.class) @RequestBody TableDetailRequestDTO body, Principal principal) {
        tableService.updateDetailTable(tableId, body, principal);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @PreAuthorize("@authManager.hasPermission(authentication, 'TABLE_CREATE')")
    public ResponseEntity<TableResponseDTO> addTable(@Validated(TableValidationGroups.Admin.class) @RequestBody TableRequestDTO body, Principal principal) {
        TableResponseDTO table = tableService.addTable(body, principal);
        return ResponseEntity.status(201).body(table);
    }

    @GetMapping("/{tableId}/statistics")
    @PreAuthorize("@authManager.hasPermission(authentication, 'TABLE_READ')")
    public ResponseEntity<List<TableStatisticsResponseDTO>> getTableStatistics(@PathVariable("tableId") Long tableId, @Valid TableStatisticsFilterDTO queryParam, Principal principal) {
        List<TableStatisticsResponseDTO> res = this.tableService.getTableStatistics(tableId, queryParam, true, principal);
        return ResponseEntity.status(200).body(res);
    }

}
