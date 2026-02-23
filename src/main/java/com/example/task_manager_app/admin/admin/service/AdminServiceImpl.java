package com.example.task_manager_app.admin.admin.service;

import com.example.task_manager_app.admin.admin.dto.AddAdminRequest;
import com.example.task_manager_app.admin.admin.interfaces.AdminService;
import com.example.task_manager_app.common.dto.FilterRequest;
import com.example.task_manager_app.common.dto.ProfileRequest;
import com.example.task_manager_app.common.exception.NotFoundException;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.enums.PrivilegeEnum;
import com.example.task_manager_app.user.interfaces.UserService;
import com.example.task_manager_app.user.repository.UserRepository;
import com.example.task_manager_app.user.repository.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserService userService;
    private final UserRepository userRepository;

    public AdminServiceImpl(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public Page<UserEntity> getAdmins(FilterRequest filter, Pageable pageable) {
        return this.userRepository.findAll(UserSpecification.filterBy(filter, PrivilegeEnum.ADMIN), pageable);
    }

    @Override
    public void addAdmin(AddAdminRequest body) throws Exception {
        this.userService.addUser(body, PrivilegeEnum.ADMIN);
    }

    @Override
    public UserEntity updateAdmin(long adminId, ProfileRequest body) throws Exception {
        return this.userService.updateUser(adminId, body, PrivilegeEnum.ADMIN);
    }

    @Override
    public void removeAdmin(long adminId) throws Exception {
        userRepository.findByIdAndPrivilege(adminId, PrivilegeEnum.ADMIN)
                .ifPresentOrElse(
                        user -> userRepository.deleteById(adminId), () -> {
                            throw new NotFoundException("user not found");
                        });
    }
}
