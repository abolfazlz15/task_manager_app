package com.example.task_manager_app.admin.user.service;

import com.example.task_manager_app.admin.user.interfaces.AdminUserService;
import com.example.task_manager_app.common.dto.FilterRequest;
import com.example.task_manager_app.common.dto.ProfileRequest;
import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.enums.PrivilegeEnum;
import com.example.task_manager_app.user.interfaces.UserService;
import com.example.task_manager_app.user.repository.UserRepository;
import com.example.task_manager_app.user.repository.UserSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;
    private final UserService userService;

    public AdminUserServiceImpl(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public CustomPageResponse<UserEntity> getUsers(FilterRequest filter, Pageable pageable) {
        return CustomPageResponse.fromPage(this.userRepository.findAll(UserSpecification.filterBy(filter, PrivilegeEnum.USER), pageable));
    }

    @Override
    public UserEntity updateUser(long userId, ProfileRequest body) throws Exception {
        return  this.userService.updateUser(userId, body, PrivilegeEnum.USER);
    }
}
