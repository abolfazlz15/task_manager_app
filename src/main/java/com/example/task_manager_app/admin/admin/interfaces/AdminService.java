package com.example.task_manager_app.admin.admin.interfaces;

import com.example.task_manager_app.admin.admin.dto.AddAdminRequest;
import com.example.task_manager_app.common.dto.FilterRequest;
import com.example.task_manager_app.common.dto.ProfileRequest;
import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.user.entity.UserEntity;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    public void removeAdmin(long adminId) throws Exception;
    public CustomPageResponse<UserEntity> getAdmins(FilterRequest filter, Pageable pageable);
    public void addAdmin(AddAdminRequest body) throws Exception;
    public UserEntity updateAdmin(long adminId, ProfileRequest body) throws Exception;
}
