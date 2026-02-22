package com.example.task_manager_app.admin.user.interfaces;

import com.example.task_manager_app.common.dto.FilterRequest;
import com.example.task_manager_app.common.dto.ProfileRequest;
import com.example.task_manager_app.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface AdminUserService {
    public Page<UserEntity> getUsers(FilterRequest filter, Pageable pageable);
    public UserEntity updateUser(long userId, ProfileRequest body) throws Exception;
}
