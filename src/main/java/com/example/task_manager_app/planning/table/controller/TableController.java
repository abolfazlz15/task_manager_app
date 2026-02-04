package com.example.task_manager_app.planning.table.controller;

import com.example.task_manager_app.planning.table.dto.*;
import com.example.task_manager_app.planning.table.interfaces.TableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/table")
@RequiredArgsConstructor
public class TableController {
    private final TableService tableService;

    @PostMapping("/create")
    public ResponseEntity<TableResponse> createTable(@RequestBody @Valid CreateTableRequest body, Principal principal) {
        TableResponse table = tableService.create(body, principal);
        return ResponseEntity.status(201).body(table);
    }

    @PutMapping("/update/detail/{tableId}")
    public ResponseEntity<Void> updateDetailTable(@PathVariable Long tableId, @RequestBody @Valid UpdateTableDetailRequest body, Principal principal) {
        tableService.updateDetail(tableId, body, principal);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{tableId}")
    public ResponseEntity<TableResponse> updateTable(@PathVariable Long tableId, @RequestBody @Valid UpdateTableRequest body, Principal principal) {
        TableResponse table = tableService.update(tableId, body, principal);
        return ResponseEntity.status(201).body(table);
    }

    @GetMapping("/detail/{tableId}")
    public ResponseEntity<TableDetailResponse> updateTable(@PathVariable Long tableId, Principal principal) {
        TableDetailResponse table = tableService.detail(tableId, principal);
        return ResponseEntity.status(201).body(table);
    }

    @GetMapping("/statistics/{tableId}")
    public ResponseEntity<List<TableStatisticsResponse>> getTableStatistics(Principal principal, @PathVariable("tableId") Long tableId, @Valid GetTableStatisticsRequest queryParam) {
        List<TableStatisticsResponse> res = this.tableService.getTableStatistics(principal, tableId, queryParam);
        return ResponseEntity.status(200).body(res);
    }
}
