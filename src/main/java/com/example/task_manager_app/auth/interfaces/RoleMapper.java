package com.example.task_manager_app.auth.interfaces;

import com.example.task_manager_app.auth.dto.RoleResponseDTO;
import com.example.task_manager_app.auth.entity.RoleEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    List<RoleResponseDTO> toResponseDTO(List<RoleEntity> roles);
    RoleResponseDTO toResponseDTO(RoleEntity roles);
}
