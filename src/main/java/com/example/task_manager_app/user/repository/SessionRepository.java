package com.example.task_manager_app.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task_manager_app.user.entity.SessionEntity;


@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

}