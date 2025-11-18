package com.example.task_manager_app.user.interfaces;

import com.example.task_manager_app.user.entity.UserEntity;

import java.security.Principal;

public interface UserService {
    public UserEntity getProfile(Principal principal);
}
