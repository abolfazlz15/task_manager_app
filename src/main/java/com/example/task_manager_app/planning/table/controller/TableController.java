package com.example.task_manager_app.planning.table.controller;

import com.example.task_manager_app.planning.table.dto.CreateTableRequest;
import com.example.task_manager_app.planning.table.dto.TableResponse;
import com.example.task_manager_app.planning.table.dto.UpdateTableDetailRequest;
import com.example.task_manager_app.planning.table.dto.UpdateTableRequest;
import com.example.task_manager_app.planning.table.interfaces.TableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
}
