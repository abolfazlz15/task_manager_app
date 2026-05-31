package com.example.task_manager_app.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseAuthRequestDTO {
    @Size(max=25)
    private String username;

    @Email
    private String email;

    @Pattern(regexp = "\\+?[0-9]{10,15}")
    private String phone;
}
