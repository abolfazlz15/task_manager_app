package com.example.task_manager_app.auth.config;

import com.example.task_manager_app.planning.table.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;


@Component("ownershipManager")
@RequiredArgsConstructor
public class OwnershipManager {
    private final TableRepository tableRepository;

    public boolean isTableOwner(Authentication authentication, Long tableId) {
        if (authentication == null || tableId == null) {
            return false;
        }
        Long userId;
        System.out.println(authentication.getName());
        System.out.println(authentication.getPrincipal().toString());
        try {
            userId = Long.parseLong(authentication.getName());
        } catch (NumberFormatException e) {
            return false;
        }
        return tableRepository.existsByUserIdAndId(userId, tableId);
    }
}
