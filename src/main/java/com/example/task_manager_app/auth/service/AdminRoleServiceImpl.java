package com.example.task_manager_app.auth.service;

import com.example.task_manager_app.auth.dto.RoleResponseDTO;
import com.example.task_manager_app.auth.dto.RoleRequestDTO;
import com.example.task_manager_app.auth.interfaces.AdminRoleService;
import com.example.task_manager_app.auth.entity.RoleEntity;
import com.example.task_manager_app.auth.interfaces.RoleMapper;
import com.example.task_manager_app.auth.repository.RoleRepository;
import com.example.task_manager_app.common.exception.BadRequestException;
import com.example.task_manager_app.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminRoleServiceImpl implements AdminRoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleResponseDTO> getRoles() {
        List<RoleEntity> roles = roleRepository.findAll();
        return roleMapper.toResponseDTO(roles);
    }

    @Override
    @Transactional
    public void addRole(RoleRequestDTO body) {
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
    public RoleResponseDTO updateRole(long roleId, RoleRequestDTO body) {
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("role not found"));

        if(body.getPermissions() != null) {
            role.setPermissions(body.getPermissions());
        }

        if (body.getName() != null && !role.getName().equals(body.getName())) {
            if(roleRepository.existsByName(body.getName())) {
                throw new BadRequestException("role name already exists");
            }

            role.setName(body.getName());
        }


        RoleEntity updatedRole = roleRepository.save(role);
        return roleMapper.toResponseDTO(updatedRole);
    }

    @Override
    @Transactional
    public void deleteRole(long roleId) {
        Optional<RoleEntity> role = this.roleRepository.findById(roleId);
        if(role.isEmpty()) {
            throw new NotFoundException("role not found");
        }

        this.roleRepository.deleteUserRoleByRoleId(roleId);
        this.roleRepository.deleteById(roleId);
    }
}
