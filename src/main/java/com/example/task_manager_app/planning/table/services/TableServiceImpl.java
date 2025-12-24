package com.example.task_manager_app.planning.table.services;

import com.example.task_manager_app.common.exception.ForbiddenException;
import com.example.task_manager_app.common.exception.NotFoundException;
import com.example.task_manager_app.planning.table.dto.CreateTableRequest;
import com.example.task_manager_app.planning.table.dto.TableResponse;
import com.example.task_manager_app.planning.table.dto.UpdateTableRequest;
import com.example.task_manager_app.planning.table.entity.TableEntity;
import com.example.task_manager_app.planning.table.interfaces.TableMapper;
import com.example.task_manager_app.planning.table.interfaces.TableService;
import com.example.task_manager_app.planning.table.repository.ColumnRepository;
import com.example.task_manager_app.planning.table.repository.TableRepository;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {
    private final TableRepository tableRepository;
    private final UserRepository userRepository;
    private final TableMapper tableMapper;
    private final ColumnRepository columnRepository;

    public TableResponse create(CreateTableRequest tableData, Principal principal) {
        UserEntity user = userRepository.findById(Long.parseLong(principal.getName()))
        .orElseThrow(() -> new NotFoundException("User Not Found"));

        TableEntity table = new TableEntity();
        table.setName(tableData.getName());
        table.setDescription(tableData.getDescription());
        table.setUser(user);

        TableEntity tableSaved = tableRepository.save(table);
        return new TableResponse(
                tableSaved.getId(),
                tableSaved.getName(),
                tableSaved.getDescription(),
                tableSaved.getCreatedAt()
        );
    }

    @Transactional
    public void update(Long tableId, UpdateTableRequest tableData, Principal principal) {
        TableEntity existingTable = tableRepository.findById(tableId)
                .orElseThrow(() -> new NotFoundException("Table Not Found"));

        if (!existingTable.getUser().getId().equals(Long.parseLong(principal.getName()))) {
            throw new ForbiddenException("Access Denied");
        }

        columnRepository.deleteByTableId(tableId);
        existingTable.getColumns().clear();

        tableMapper.updateEntityFromDto(tableData, existingTable);
        tableRepository.save(existingTable);
    }
}
