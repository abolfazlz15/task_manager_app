package com.example.task_manager_app.user.dto;

import com.example.task_manager_app.auth.entity.RoleEntity;
import com.example.task_manager_app.user.enums.UserGenderEnum;
import com.example.task_manager_app.user.enums.UserStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class ProfileResponseDTO {
    private Long id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private Instant birthday;
    private UserGenderEnum gender;
    private String bio;
    private String profileImage;
    private UserStatusEnum status;
    private Set<RoleEntity> roles;
}
