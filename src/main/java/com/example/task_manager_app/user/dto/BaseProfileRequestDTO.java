package com.example.task_manager_app.user.dto;

import com.example.task_manager_app.common.validation.ValidationGroups;
import com.example.task_manager_app.user.enums.UserGenderEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Getter
@Setter
public class BaseProfileRequestDTO {
    @Max(100)
    @NotBlank(groups = ValidationGroups.Create.class)
    @NotNull(groups = ValidationGroups.Create.class)
    private String username;

    @Max(100)
    private String firstName;

    @Max(100)
    private String lastName;

    @PastOrPresent
    private Instant birthday;

    private UserGenderEnum gender;

    @Max(5000)
    private String bio;

    private MultipartFile file;
}
