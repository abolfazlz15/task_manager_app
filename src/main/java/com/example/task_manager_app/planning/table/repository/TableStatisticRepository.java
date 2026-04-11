package com.example.task_manager_app.planning.table.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.example.task_manager_app.planning.table.entity.TableEntity;
import com.example.task_manager_app.planning.table.entity.TableStatisticEntity;

import io.lettuce.core.dynamic.annotation.Param;

public interface TableStatisticRepository extends JpaRepository<TableStatisticEntity, Long>, JpaSpecificationExecutor<TableStatisticEntity> {
    @Query("SELECT ts FROM TableStatisticEntity ts WHERE ts.table = :table AND ts.createdAt BETWEEN :from AND :to")
    List<TableStatisticEntity> findByTableAndBetween(@Param("table") TableEntity table, @Param("from") Instant from, @Param("to") Instant to);

    @Query("SELECT SUM(ts.score) / :count FROM TableStatisticEntity ts WHERE ts.table.id = :tableId AND ts.table.user.id = :userId AND ts.createdAt BETWEEN :from AND :to")
    Optional<Short> getAvgScoreBetween(@Param("tableId") long tableId, @Param("userId") long userId, @Param("from") Instant from, @Param("to") Instant to, @Param("count") int count);

    @Query("SELECT AVG(ts.score) FROM TableStatisticEntity ts WHERE ts.table.id = :tableId AND ts.table.user.id = :userId")
    Optional<Short> getAvgScore(@Param("tableId") long tableId, @Param("userId") long userId);

    Optional<TableStatisticEntity> findFirstByTableOrderByCreatedAtDesc(TableEntity table);
}
