package com.example.task_manager_app.auth.repository;

import com.example.task_manager_app.auth.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Override
    List<RoleEntity> findAllById(Iterable<Long> ids);

}