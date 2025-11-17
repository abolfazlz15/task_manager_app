package com.example.task_manager_app.user.repository;

import com.example.task_manager_app.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task_manager_app.user.entity.SessionEntity;

import java.util.Optional;


@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {
    Optional<SessionEntity> findByKey(String key);

}