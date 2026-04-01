package com.example.task_manager_app.user.interfaces;

import com.example.task_manager_app.user.dto.AdminRequestDTO;
import com.example.task_manager_app.user.dto.ProfileResponseDTO;
import com.example.task_manager_app.user.dto.UserRequestDTO;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.enums.PrivilegeEnum;

import java.io.IOException;
import java.security.Principal;

public interface UserService {
    public ProfileResponseDTO getProfile(Principal principal);
    public ProfileResponseDTO updateUser(Principal principal, UserRequestDTO body) throws IOException;
    public UserEntity updateProfile(long userId, AdminRequestDTO body, PrivilegeEnum side) throws IOException;
    public void addProfile(AdminRequestDTO body, PrivilegeEnum side) throws Exception;
}
