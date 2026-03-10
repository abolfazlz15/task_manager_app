package com.example.task_manager_app.user.interfaces;

import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.user.dto.AdminRequestDTO;
import com.example.task_manager_app.user.dto.ProfileResponseDTO;
import com.example.task_manager_app.user.dto.UserRequestDTO;
import com.example.task_manager_app.user.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    CustomPageResponse<ProfileResponseDTO> toProfileResponseDTO(CustomPageResponse<UserEntity> entity);
    ProfileResponseDTO toProfileResponseDTO(UserEntity entity);
    AdminRequestDTO toAdminRequestDTO(UserRequestDTO dto);
}
