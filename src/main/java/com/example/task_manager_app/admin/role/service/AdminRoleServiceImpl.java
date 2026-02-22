package com.example.task_manager_app.admin.role.service;

import com.example.task_manager_app.admin.role.dto.AddRoleRequest;
import com.example.task_manager_app.admin.role.interfaces.AdminRoleService;
import com.example.task_manager_app.auth.entity.RoleEntity;
import com.example.task_manager_app.auth.repository.RoleRepository;
import com.example.task_manager_app.common.exception.BadRequestException;
import com.example.task_manager_app.common.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminRoleServiceImpl implements AdminRoleService {
    private final RoleRepository roleRepository;

    public AdminRoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleEntity> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public void addRole(AddRoleRequest body) {
        List<RoleEntity> existRole = this.roleRepository.findByName(body.getName());

        if(!existRole.isEmpty()) {
            throw new BadRequestException("Role with this name already exists");
        }

        RoleEntity role = new RoleEntity();

        role.setName(body.getName());
        role.setPermissions(body.getPermissions());

        this.roleRepository.save(role);
    }

    @Override
    public RoleEntity updateRole(long roleId, AddRoleRequest body) {
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("role not found"));

        role.setPermissions(body.getPermissions());

        if (!role.getName().equals(body.getName())) {
            if(roleRepository.existsByName(body.getName())) {
                throw new BadRequestException("role name already exists");
            }

            role.setName(body.getName());
        }


        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void removeRole(long roleId) {
        Optional<RoleEntity> role = this.roleRepository.findById(roleId);
        if(role.isEmpty()) {
            throw new NotFoundException("role not found");
        }

        this.roleRepository.deleteRoleById(roleId);
        this.roleRepository.deleteById(roleId);
    }
}
