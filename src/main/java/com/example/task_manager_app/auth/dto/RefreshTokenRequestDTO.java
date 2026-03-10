package com.example.task_manager_app.auth.dto;

import com.example.task_manager_app.common.validation.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequestDTO {
    @NotBlank(groups = ValidationGroups.Update.class)
    private String refreshToken;
}
