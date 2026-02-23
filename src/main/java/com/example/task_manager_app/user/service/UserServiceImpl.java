package com.example.task_manager_app.user.service;

import com.example.task_manager_app.auth.entity.RoleEntity;
import com.example.task_manager_app.auth.repository.RoleRepository;
import com.example.task_manager_app.common.exception.BadRequestException;
import com.example.task_manager_app.common.exception.NotFoundException;
import com.example.task_manager_app.file.interfaces.FileService;
import com.example.task_manager_app.common.dto.ProfileRequest;
import com.example.task_manager_app.user.dto.UpdateProfileRequest;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.enums.PrivilegeEnum;
import com.example.task_manager_app.user.enums.UserStatusEnum;
import com.example.task_manager_app.user.interfaces.UserService;
import com.example.task_manager_app.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FileService fileService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,  FileService fileService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.fileService = fileService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity getProfile(Principal principal) {
        Optional<UserEntity> user = userRepository.findById(Long.parseLong(principal.getName()));

        if(user.isEmpty()) {
            throw new NotFoundException("user not found");
        }

        return user.get();
    }

    @Override
    public UserEntity updateProfile(Principal principal, UpdateProfileRequest body) throws IOException {
        ProfileRequest profile = new ProfileRequest();

        profile.setFirstName(body.getFirstName());
        profile.setLastName(body.getLastName());
        profile.setBirthday(body.getBirthday());
        profile.setGender(body.getGender());
        profile.setBio(body.getBio());
        profile.setUsername(body.getUsername());
        profile.setFile(body.getFile());

        return updateUser(Long.parseLong(principal.getName()), profile, PrivilegeEnum.USER);
    }

    @Override
    public void addUser(ProfileRequest body, PrivilegeEnum privilege) throws Exception {
        boolean isProfileExist = userRepository.existsByUsernameOrEmailOrPhone(body.getUsername(), body.getEmail(), body.getPhone());
        if(isProfileExist) {
            throw new BadRequestException("Invalid identifier please verify your input and try again");
        }

        UserEntity profile = new UserEntity();

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
    public UserEntity updateUser(long userId, ProfileRequest body, PrivilegeEnum privilege) throws IOException {
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
