package com.example.task_manager_app.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyRegisterRequest {
    @NotBlank
    @Size(max=50)
    @Email
    private String email;

    @NotBlank
    @Size(max=25)
    private String username;

    @NotBlank
    @Size(min=6, max=40)
    private String password;

    @NotBlank
    @Size(min=4)
    private String otpCode;
}
