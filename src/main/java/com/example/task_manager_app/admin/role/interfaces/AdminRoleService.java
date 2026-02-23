package com.example.task_manager_app.admin.role.interfaces;

import com.example.task_manager_app.admin.role.dto.AddRoleRequest;
import com.example.task_manager_app.auth.entity.RoleEntity;

import java.util.List;

public interface AdminRoleService {
    public List<RoleEntity> getRoles();
    public void  addRole(AddRoleRequest body);
    public RoleEntity updateRole(long roleId, AddRoleRequest body);
    public void removeRole(long roleId);
}
