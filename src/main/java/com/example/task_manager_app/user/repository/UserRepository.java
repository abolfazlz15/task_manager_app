package com.example.task_manager_app.user.repository;

import java.util.Optional;
import java.util.Set;

import com.example.task_manager_app.user.enums.PrivilegeEnum;
import com.example.task_manager_app.user.enums.UserStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.task_manager_app.user.entity.UserEntity;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByPhone(String phone);
    Optional<UserEntity> findByUsername(String phone);
    Optional<UserEntity> findByIdAndPrivilege(long userId, PrivilegeEnum privilege);

    boolean existsByIdAndStatusIs(Long id,  UserStatusEnum status);
    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
    boolean existsByIdAndPrivilegeIs(Long id, PrivilegeEnum privilege);


    @Query("SELECT u FROM UserEntity u WHERE u.username = :username OR u.email = :email OR u.phone = :phone")
    UserEntity findByUsernameOrEmailOrPhone(@Param("username") String username, @Param("email") String email, @Param("phone") String phone);

    @Query("SELECT CASE WHEN COUNT(u) > 0THEN TRUE ELSE FALSE END FROM UserEntity u WHERE u.username = :username OR u.email = :email OR u.phone = :phone")
    boolean existsByUsernameOrEmailOrPhone(@Param("username") String username, @Param("email") String email, @Param("phone") String phone);
}
