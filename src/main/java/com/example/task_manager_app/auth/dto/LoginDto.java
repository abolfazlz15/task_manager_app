package com.example.task_manager_app.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginDto {

    private String username;

    @Email
    private String email;

    private String phone;

    @NotBlank
    private String password;
}
