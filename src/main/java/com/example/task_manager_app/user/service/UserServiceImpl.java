package com.example.task_manager_app.user.service;

import com.example.task_manager_app.common.exception.BadRequestException;
import com.example.task_manager_app.common.exception.NotFoundException;
import com.example.task_manager_app.file.interfaces.FileService;
import com.example.task_manager_app.user.dto.UpdateProfileRequest;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.interfaces.UserService;
import com.example.task_manager_app.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FileService fileService;

    public UserServiceImpl(UserRepository userRepository,  FileService fileService) {
        this.userRepository = userRepository;
        this.fileService = fileService;
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
        UserEntity user = getProfile(principal);

        if(Objects.nonNull(body.getUsername())) {
            if(userRepository.existsByUsername(body.getUsername())) {
                throw new BadRequestException("username already exists");
            }

            user.setUsername(body.getUsername());
        }

        if(Objects.nonNull(body.getBirthday())) {
            user.setBirthday(body.getBirthday());
        }

        if(Objects.nonNull(body.getGender())) {
            user.setGender(body.getGender());
        }

        if(Objects.nonNull(body.getFirstName())) {
            user.setFirstName(body.getFirstName());
        }

        if(Objects.nonNull(body.getLastName())) {
            user.setLastName(body.getLastName());
        }

        if(Objects.nonNull(body.getBio())) {
            user.setBio(body.getBio());
        }

        if (Objects.nonNull(body.getFile()) && !body.getFile().isEmpty()) {
           String filename = fileService.saveFileToStorage(body.getFile());
           user.setProfileImage(filename);
        }

        return userRepository.save(user);
    }
}
