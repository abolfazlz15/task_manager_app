package com.example.task_manager_app.user.repository;

import com.example.task_manager_app.user.entity.UserEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username OR u.email = :email OR u.phone = :phone")
    UserEntity findByUsernameOrEmailOrPhone(@Param("username") String username, @Param("email") String email, @Param("phone") String phone);
}
