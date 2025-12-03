package com.example.task_manager_app.user.interfaces;

import com.example.task_manager_app.user.dto.UpdateProfileRequest;
import com.example.task_manager_app.user.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

public interface UserService {
    public UserEntity getProfile(Principal principal);
    public UserEntity updateProfile(Principal principal, UpdateProfileRequest body) throws IOException;
}
