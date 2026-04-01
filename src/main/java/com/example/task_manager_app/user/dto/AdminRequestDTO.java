package com.example.task_manager_app.user.dto;

import com.example.task_manager_app.common.validation.ValidationGroups;
import com.example.task_manager_app.user.enums.UserStatusEnum;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminRequestDTO extends BaseProfileRequestDTO {
    @NotBlank(groups = ValidationGroups.Create.class)
    @NotNull(groups = ValidationGroups.Create.class)
    private String password;

    @Email
    @NotBlank(groups = ValidationGroups.Create.class)
    @NotNull(groups = ValidationGroups.Create.class)
    private String email;

    @Pattern(regexp = "\\+?[0-9]{10,15}")
    private String phone;

    private Set<String> roles;

    private UserStatusEnum status;
}
