package com.example.task_manager_app.user.interfaces;

import com.example.task_manager_app.common.dto.ProfileRequest;
import com.example.task_manager_app.user.dto.UpdateProfileRequest;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.enums.PrivilegeEnum;

import java.io.IOException;
import java.security.Principal;

public interface UserService {
    public UserEntity getProfile(Principal principal);
    public UserEntity updateProfile(Principal principal, UpdateProfileRequest body) throws IOException;
    public UserEntity updateUser(long userId, ProfileRequest body, PrivilegeEnum side) throws IOException;
    public void addUser(ProfileRequest body, PrivilegeEnum side) throws Exception;
}
