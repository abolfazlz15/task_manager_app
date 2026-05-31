package com.example.task_manager_app.user.interfaces;

import com.example.task_manager_app.common.dto.FilterRequestDTO;
import com.example.task_manager_app.user.dto.AdminRequestDTO;
import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.user.dto.ProfileResponseDTO;
import org.springframework.data.domain.Pageable;


public interface AdminUserService {
    public CustomPageResponse<ProfileResponseDTO> getUsers(FilterRequestDTO filter, Pageable pageable);
    public ProfileResponseDTO updateUser(long userId, AdminRequestDTO body) throws Exception;
}
