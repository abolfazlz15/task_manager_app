package com.example.task_manager_app.planning.table.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.task_manager_app.planning.table.entity.TableEntity;
import com.example.task_manager_app.planning.table.entity.TableStatisticEntity;

import io.lettuce.core.dynamic.annotation.Param;

public interface TableStatisticRepository extends JpaRepository<TableStatisticEntity, Long> {
    @Query("SELECT ts FROM TableStatisticEntity ts WHERE ts.table = :table AND ts.createdAt BETWEEN :from AND :to")
    List<TableStatisticEntity> findByTableAndBetween(@Param("table") TableEntity table, @Param("from") Instant from, @Param("to") Instant to);
}
