package com.example.task_manager_app.auth.service;

import com.example.task_manager_app.auth.dto.LoginDto;
import com.example.task_manager_app.auth.interfaces.AuthService;
import com.example.task_manager_app.common.exception.BadRequestException;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository,  BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void login(LoginDto body, String ip, HttpHeaders headers) {
        UserEntity user = userRepository.findByUsernameOrEmailOrPhone(body.getUsername(), body.getEmail(), body.getPhone());

        if(user == null) {
            throw new BadRequestException("Invalid identifier please verify your input and try again");
        }

        boolean isMatch = passwordEncoder.matches(body.getPassword(), user.getPassword());

        if(!isMatch) {
            throw new BadRequestException("invalid password");
        }

        // TODO: generate JWT token
    }
}
