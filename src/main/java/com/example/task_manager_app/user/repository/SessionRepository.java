package com.example.task_manager_app.user.repository;

import java.time.Instant;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.task_manager_app.user.entity.SessionEntity;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    Optional<SessionEntity> findByKey(String key);

    @Modifying
    @Query("UPDATE SessionEntity s SET s.deletedAt = CURRENT_TIMESTAMP WHERE s.key = :key AND s.user.id = :userId AND s.expireAt >= :now AND s.deletedAt IS NULL")
    int softDeleteByKeyAndUserId(@Param("key") String key, @Param("userId") Long userId, @Param("now") Instant now);
}