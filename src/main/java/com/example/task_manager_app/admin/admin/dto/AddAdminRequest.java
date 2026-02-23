package com.example.task_manager_app.admin.admin.dto;

import com.example.task_manager_app.common.dto.ProfileRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddAdminRequest extends ProfileRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;
}
