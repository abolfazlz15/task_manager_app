package com.example.task_manager_app.user.service;

import com.example.task_manager_app.user.dto.ProfileResponseDTO;
import com.example.task_manager_app.user.interfaces.AdminService;
import com.example.task_manager_app.common.dto.FilterRequestDTO;
import com.example.task_manager_app.user.dto.AdminRequestDTO;
import com.example.task_manager_app.common.exception.NotFoundException;
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
public class AdminServiceImpl implements AdminService {
    private final UserService userService;
    private final UserRepository userRepository;
    private UserMapper userMapper;

    @Override
    public CustomPageResponse<ProfileResponseDTO> getAdmins(FilterRequestDTO filter, Pageable pageable) {
        CustomPageResponse<UserEntity> admins = CustomPageResponse.fromPage(
                this.userRepository.findAll(UserSpecification.filterBy(filter, PrivilegeEnum.ADMIN), pageable));

        return userMapper.toProfileResponseDTO(admins);
    }

    @Override
    public void addAdmin(AdminRequestDTO body) throws Exception {
        this.userService.addUser(body, PrivilegeEnum.ADMIN);
    }

    @Override
    public ProfileResponseDTO updateAdmin(long adminId, AdminRequestDTO body) throws Exception {
        UserEntity updatedAdmin = this.userService.updateProfile(adminId, body, PrivilegeEnum.ADMIN);
        return userMapper.toProfileResponseDTO(updatedAdmin);
    }

    @Override
    public void deleteAdmin(long adminId) throws Exception {
        userRepository.findByIdAndPrivilege(adminId, PrivilegeEnum.ADMIN)
                .ifPresentOrElse(
                        user -> userRepository.deleteById(adminId),
                        () -> {
                            throw new NotFoundException("user not found");
                        }
                );
    }
}
