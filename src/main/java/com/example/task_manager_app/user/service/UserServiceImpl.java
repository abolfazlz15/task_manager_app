package com.example.task_manager_app.user.service;

import com.example.task_manager_app.common.exception.NotFoundException;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.interfaces.UserService;
import com.example.task_manager_app.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getProfile(Principal principal) {
        Optional<UserEntity> user = userRepository.findById(Long.parseLong(principal.getName()));

        if(user.isEmpty()) {
            throw new NotFoundException("user not found");
        }

        return user.get();
    }

}
