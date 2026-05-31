package com.example.task_manager_app.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyRegisterRequestDTO extends BaseAuthRequestDTO {
    @NotBlank
    @Size(min=6, max=40)
    private String password;

    @NotBlank
    @Size(min=4)
    private String otpCode;
}
