package com.example.task_manager_app.common.dto;

import com.example.task_manager_app.user.enums.UserGenderEnum;
import com.example.task_manager_app.user.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;
    private Instant birthday;
    private UserGenderEnum gender;
    private String bio;
    private MultipartFile file;
    private String username;
    private Set<String> roles;
    private UserStatusEnum status;
}
