package com.example.task_manager_app.planning.table.repository;

import com.example.task_manager_app.planning.table.entity.ColumnEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnRepository extends JpaRepository<ColumnEntity, Long> {
    void deleteByTableId(Long tableId);
}
