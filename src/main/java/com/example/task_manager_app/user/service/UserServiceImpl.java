package com.example.task_manager_app.user.service;

import com.example.task_manager_app.auth.entity.RoleEntity;
import com.example.task_manager_app.auth.repository.RoleRepository;
import com.example.task_manager_app.user.dto.AdminRequestDTO;
import com.example.task_manager_app.common.exception.BadRequestException;
import com.example.task_manager_app.common.exception.NotFoundException;
import com.example.task_manager_app.file.interfaces.FileService;
import com.example.task_manager_app.user.dto.ProfileResponseDTO;
import com.example.task_manager_app.user.dto.UserRequestDTO;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.enums.PrivilegeEnum;
import com.example.task_manager_app.user.enums.UserStatusEnum;
import com.example.task_manager_app.user.interfaces.UserMapper;
import com.example.task_manager_app.user.interfaces.UserService;
import com.example.task_manager_app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FileService fileService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public ProfileResponseDTO getProfile(Principal principal) {
        UserEntity user = userRepository.findById(Long.parseLong(principal.getName()))
                .orElseThrow(() -> new NotFoundException("user not found"));;

        return userMapper.toProfileResponseDTO(user);
    }

    @Override
    public ProfileResponseDTO updateUser(Principal principal, UserRequestDTO body) throws IOException {
        AdminRequestDTO profile = userMapper.toAdminRequestDTO(body);

        UserEntity updatedUser = updateProfile(Long.parseLong(principal.getName()), profile, PrivilegeEnum.USER);
        return userMapper.toProfileResponseDTO(updatedUser);
    }

    @Override
    public void addProfile(AdminRequestDTO body, PrivilegeEnum privilege) throws Exception {
        boolean isProfileExist = userRepository.existsByUsernameOrEmailOrPhone(body.getUsername(), body.getEmail(), body.getPhone());
        if(isProfileExist) {
            throw new BadRequestException("Invalid identifier please verify your input and try again");
        }

        UserEntity profile = new UserEntity();

        profile.setPrivilege(privilege);
        profile.setEmail(body.getEmail());
        profile.setUsername(body.getUsername());
        profile.setPassword(passwordEncoder.encode(body.getPassword()));
        profile.setStatus(UserStatusEnum.ACTIVE);
        profile.setFirstName(body.getFirstName());
        profile.setLastName(body.getLastName());
        profile.setBirthday(body.getBirthday());
        profile.setGender(body.getGender());
        profile.setBio(body.getBio());
        profile.setPhone(body.getPhone());

        if (Objects.nonNull(body.getFile()) && !body.getFile().isEmpty()) {
            String filename = fileService.saveFileToStorage(body.getFile());
            profile.setProfileImage(filename);
        }

        if(privilege.equals(PrivilegeEnum.ADMIN) && body.getRoles() != null) {
            Set<RoleEntity> roles = roleRepository.findByName(body.getRoles());
            profile.setRoles(roles);
        }

        userRepository.save(profile);
    }

    @Override
    public UserEntity updateProfile(long userId, AdminRequestDTO body, PrivilegeEnum privilege) throws IOException {
        UserEntity user = userRepository.findByIdAndPrivilege(userId, privilege)
                .orElseThrow(() -> new NotFoundException("user not found"));

        if(body.getFirstName() != null) {
            user.setFirstName(body.getFirstName());
        }

        if(body.getLastName() != null) {
            user.setLastName(body.getLastName());
        }

        if(body.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(body.getPassword()));
        }

        if(body.getEmail() != null) {
            if(userRepository.existsByEmail(body.getEmail())) {
                throw new BadRequestException("Email already exists");
            }
            user.setEmail(body.getEmail());
        }

        if(body.getPhone() != null) {
            if(userRepository.existsByPhone(body.getPhone())) {
                throw new BadRequestException("Phone already exists");
            }
            user.setPhone(body.getPhone());
        }

        if(body.getUsername() != null) {
            if(userRepository.existsByUsername(body.getUsername())) {
                throw new BadRequestException("Username already exists");
            }
            user.setUsername(body.getUsername());
        }

        if(body.getBirthday() != null) {
            user.setBirthday(body.getBirthday());
        }

        if(body.getGender() != null) {
            user.setGender(body.getGender());
        }

        if(body.getBio() != null) {
            user.setBio(body.getBio());
        }

        if(body.getStatus() != null && !privilege.equals(PrivilegeEnum.SUPER_ADMIN)) {
            user.setStatus(body.getStatus());
        }

        if (body.getFile() != null && !body.getFile().isEmpty()) {
            String filename = fileService.saveFileToStorage(body.getFile());
            user.setProfileImage(filename);
        }

        if (privilege.equals(PrivilegeEnum.ADMIN) && body.getRoles() != null) {
            Set<RoleEntity> roles = roleRepository.findByName(body.getRoles());
            user.setRoles(roles);
        }

        return userRepository.save(user);
    }
}
