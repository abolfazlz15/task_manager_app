package com.example.task_manager_app.planning.table.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task_manager_app.planning.table.entity.TableEntity;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long>  {}
