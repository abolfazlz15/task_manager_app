package com.example.task_manager_app.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordRequestDTO extends BaseForgetPasswordRequestDTO {
    @NotNull
    private String token;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
