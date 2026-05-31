package com.example.task_manager_app.user.interfaces;

import com.example.task_manager_app.common.dto.FilterRequestDTO;
import com.example.task_manager_app.user.dto.AdminRequestDTO;
import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.user.dto.ProfileResponseDTO;
import org.springframework.data.domain.Pageable;

public interface AdminService {
    public void deleteAdmin(long adminId) throws Exception;
    public CustomPageResponse<ProfileResponseDTO> getAdmins(FilterRequestDTO filter, Pageable pageable);
    public void addAdmin(AdminRequestDTO body) throws Exception;
    public ProfileResponseDTO updateAdmin(long adminId, AdminRequestDTO body) throws Exception;
}
