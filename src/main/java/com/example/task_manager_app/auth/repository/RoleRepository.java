package com.example.task_manager_app.auth.repository;

import com.example.task_manager_app.auth.entity.RoleEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Override
    List<RoleEntity> findAllById(Iterable<Long> ids);
    List<RoleEntity> findByName(String name);

    boolean existsByName(String name);

    @Query("SELECT r FROM RoleEntity r WHERE r.name IN :names")
    Set<RoleEntity> findByName(@Param("names") Set<String> names);

    @Modifying
    @Query(value = "DELETE FROM user_roles WHERE role_id = :roleId", nativeQuery = true)
    void deleteRoleById(@Param("roleId") Long roleId);
}