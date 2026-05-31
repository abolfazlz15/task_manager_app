package com.example.task_manager_app.user.service;

import com.example.task_manager_app.user.dto.ProfileResponseDTO;
import com.example.task_manager_app.user.interfaces.AdminUserService;
import com.example.task_manager_app.common.dto.FilterRequestDTO;
import com.example.task_manager_app.user.dto.AdminRequestDTO;
import com.example.task_manager_app.common.response.CustomPageResponse;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.enums.PrivilegeEnum;
import com.example.task_manager_app.user.interfaces.UserMapper;
import com.example.task_manager_app.user.interfaces.UserService;
import com.example.task_manager_app.user.repository.UserRepository;
import com.example.task_manager_app.user.repository.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public CustomPageResponse<ProfileResponseDTO> getUsers(FilterRequestDTO filter, Pageable pageable) {
        CustomPageResponse<UserEntity> users = CustomPageResponse.fromPage(this.userRepository.findAll(UserSpecification.filterBy(filter, PrivilegeEnum.USER), pageable));
        return  userMapper.toProfileResponseDTO(users);
    }

    @Override
    public ProfileResponseDTO updateUser(long userId, AdminRequestDTO body) throws Exception {
        UserEntity updatedUser = this.userService.updateProfile(userId, body, PrivilegeEnum.USER);
        return userMapper.toProfileResponseDTO(updatedUser);
    }
}
