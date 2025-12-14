package com.example.task_manager_app.planning.table.services;

import com.example.task_manager_app.common.exception.NotFoundException;
import com.example.task_manager_app.planning.table.dto.CreateTableRequest;
import com.example.task_manager_app.planning.table.dto.TableResponse;
import com.example.task_manager_app.planning.table.entity.TableEntity;
import com.example.task_manager_app.planning.table.interfaces.TableService;
import com.example.task_manager_app.planning.table.repository.TableRepository;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {
    private final TableRepository tableRepository;
    private final UserRepository userRepository;

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
}
