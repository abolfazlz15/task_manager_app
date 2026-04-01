package com.example.task_manager_app.user.dto;

import com.example.task_manager_app.common.validation.ValidationGroups;
import com.example.task_manager_app.user.enums.UserGenderEnum;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Getter
@Setter
public class BaseProfileRequestDTO {
    @Size(max = 100)
    @NotBlank(groups = ValidationGroups.Create.class)
    @NotNull(groups = ValidationGroups.Create.class)
    private String username;

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @PastOrPresent
    private Instant birthday;

    private UserGenderEnum gender;

    @Size(max = 5000)
    private String bio;

    private MultipartFile file;
}
