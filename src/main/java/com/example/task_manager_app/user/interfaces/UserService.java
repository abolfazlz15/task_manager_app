package com.example.task_manager_app.user.interfaces;

import com.example.task_manager_app.user.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface UserService {
    public Optional<UserEntity> getProfile(HttpServletRequest req);
}
