package com.example.task_manager_app.planning.table.controller;

import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.planning.table.dto.*;
import com.example.task_manager_app.planning.table.interfaces.TableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/table")
@RequiredArgsConstructor
public class TableController {
    private final TableService tableService;

    @PostMapping
    public ResponseEntity<TableResponseDTO> addTable(@RequestBody @Valid TableRequestDTO body, Principal principal) {
        TableResponseDTO table = tableService.addTable(body, principal);
        return ResponseEntity.status(201).body(table);
    }

    @PutMapping("/{tableId}/detail")
//    @PreAuthorize("@ownershipManager.isTableOwner(authentication, #tableId)")
    public ResponseEntity<Void> updateDetailTable(@PathVariable Long tableId, @RequestBody @Valid TableCreateRequestDTO body, Principal principal) {
        tableService.updateDetailTable(tableId, body, principal);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{tableId}")
    @PreAuthorize("@ownershipManager.isTableOwner(authentication, #tableId)")
    public ResponseEntity<TableResponseDTO> updateTable(@PathVariable Long tableId, @RequestBody @Valid TableRequestDTO body, Principal principal) {
        TableResponseDTO table = tableService.updateTable(tableId, body, principal);
        return ResponseEntity.status(200).body(table);
    }

    @GetMapping
    public ResponseEntity<CustomPageResponse<TableResponseDTO>> getTables(TableFilterDTO filter, Principal principal) {
        CustomPageResponse<TableResponseDTO> table = tableService.getTables(filter, principal, false);
        return ResponseEntity.status(200).body(table);
    }

    @GetMapping("/{tableId}")
    @PreAuthorize("@ownershipManager.isTableOwner(authentication, #tableId)")
    public ResponseEntity<TableDetailResponseDTO> getTable(@PathVariable Long tableId, Principal principal) {
        TableDetailResponseDTO table = tableService.getTable(tableId, null, principal);
        return ResponseEntity.status(200).body(table);
    }

    @DeleteMapping("/{tableId}")
    @PreAuthorize("@ownershipManager.isTableOwner(authentication, #tableId)")
    public ResponseEntity<Void> deleteTable(@PathVariable("tableId") Long tableId) {
        tableService.deleteTable(tableId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{tableId}/statistics")
    @PreAuthorize("@ownershipManager.isTableOwner(authentication, #tableId)")
    public ResponseEntity<TableStatisticsResponseDTO> getTableStatistics(Principal principal, @PathVariable("tableId") Long tableId, @Valid TableStatisticsFilterDTO queryParam, Pageable pageable) {
        TableStatisticsResponseDTO res = this.tableService.getTableStatistics(tableId, queryParam, false,principal, pageable);
        return ResponseEntity.status(200).body(res);
    }

    @PostMapping("/{tableId}/statistics")
    @PreAuthorize("@ownershipManager.isTableOwner(authentication, #tableId)")
    public ResponseEntity<Void> addTableStatistic(Principal principal, @PathVariable("tableId") Long tableId) {
        this.tableService.addTableStatistic(tableId, principal);
        return ResponseEntity.status(200).build();
    }
}
