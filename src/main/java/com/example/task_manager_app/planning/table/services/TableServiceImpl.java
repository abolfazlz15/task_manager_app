package com.example.task_manager_app.planning.table.services;

import com.example.task_manager_app.common.exception.ForbiddenException;
import com.example.task_manager_app.common.exception.NotFoundException;
import com.example.task_manager_app.planning.table.dto.*;
import com.example.task_manager_app.planning.table.entity.ColumnEntity;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {
    private final TableRepository tableRepository;
    private final UserRepository userRepository;
    private final TableMapper tableMapper;
    private final ColumnRepository columnRepository;

    public TableResponse create(CreateTableRequest tableData, Principal principal) {
        UserEntity userProxy = userRepository.getReferenceById(Long.parseLong(principal.getName()));

        TableEntity table = new TableEntity();
        table.setName(tableData.getName());
        table.setDescription(tableData.getDescription());
        table.setUser(userProxy);

        TableEntity tableSaved = tableRepository.save(table);
        return new TableResponse(
                tableSaved.getId(),
                tableSaved.getName(),
                tableSaved.getDescription(),
                tableSaved.getCreatedAt()
        );
    }

    @Override
    @Transactional
    public void updateDetail(Long tableId, UpdateTableDetailRequest tableDetailData, Principal principal) {
        TableEntity existingTable = tableRepository.findById(tableId)
                .orElseThrow(() -> new NotFoundException("Table Not Found"));

        if (!existingTable.getUser().getId().equals(Long.parseLong(principal.getName()))) {
            throw new ForbiddenException("Access Denied");
        }
        columnRepository.deleteByTableId(tableId);
        existingTable.getColumns().clear();

        tableMapper.updateEntityFromDto(tableDetailData, existingTable);
        Double average = tableRepository.calculateAverage(existingTable.getId());
        short score = (average == null) ? 0 : (short) Math.round(average);
        existingTable.setScore(score);
        tableRepository.save(existingTable);
    }

    @Override
    @Transactional
    public TableResponse update(Long tableId, UpdateTableRequest tableData, Principal principal) {
        TableEntity table = tableRepository.findById(tableId)
                .orElseThrow(() -> new NotFoundException("Table Not Found"));

        if (!table.getUser().getId().equals(Long.parseLong(principal.getName()))) {
            throw new ForbiddenException("Access Denied");
        }
        if (tableData.getName() != null) {
            table.setName(tableData.getName());
        }
        if (tableData.getDescription() != null) {
            table.setDescription(tableData.getDescription());
        }
        TableEntity saved = tableRepository.save(table);
        return new TableResponse(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getCreatedAt()
        );
    }
    @Override
    public TableDetailResponse detail(Long tableId, Principal principal) {
        TableEntity table = tableRepository.findTableWithColumnsAndRows(tableId);

        if (table == null) {
            throw new RuntimeException("Table not found");
        }
        if (!table.getUser().getId().equals(Long.parseLong(principal.getName()))) {
            throw new ForbiddenException("Access Denied");
        }
        List<TableDetailResponse.ColumnRequest> columns = table.getColumns()
                .stream()
                .map(this::mapColumn)
                .toList();

        return new TableDetailResponse(
                table.getId(),
                table.getName(),
                table.getDescription(),
                table.getScore(),
                table.getCreatedAt(),
                columns
        );
    }

    private TableDetailResponse.ColumnRequest mapColumn(ColumnEntity column) {
        List<TableDetailResponse.RowRequest> rows = column.getRows()
                .stream()
                .map(row -> new TableDetailResponse.RowRequest(
                        row.getId(),
                        row.getIndex(),
                        row.getValue(),
                        row.getDataType()
                ))
                .toList();

        return new TableDetailResponse.ColumnRequest(
                column.getId(),
                column.getIndex(),
                column.getName(),
                rows
        );
    }

}
