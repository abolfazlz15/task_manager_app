package com.example.task_manager_app.auth.interfaces;

import com.example.task_manager_app.auth.dto.RoleResponseDTO;
import com.example.task_manager_app.auth.dto.RoleRequestDTO;
import com.example.task_manager_app.auth.entity.RoleEntity;

import java.util.List;

public interface AdminRoleService {
    public List<RoleResponseDTO> getRoles();
    public void  addRole(RoleRequestDTO body);
    public RoleResponseDTO updateRole(long roleId, RoleRequestDTO body);
    public void deleteRole(long roleId);
}
