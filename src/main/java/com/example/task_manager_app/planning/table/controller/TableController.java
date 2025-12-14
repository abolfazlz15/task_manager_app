package com.example.task_manager_app.planning.table.controller;

import com.example.task_manager_app.planning.table.dto.CreateTableRequest;
import com.example.task_manager_app.planning.table.dto.TableResponse;
import com.example.task_manager_app.planning.table.entity.TableEntity;
import com.example.task_manager_app.planning.table.interfaces.TableService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
