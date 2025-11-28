package com.example.task_manager_app.user.dto;

import com.example.task_manager_app.user.enums.UserGenderEnum;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Getter
@Setter
public class UpdateProfileRequest {
    private String username;
    private String firstName;
    private String lastName;
    @PastOrPresent
    private Instant birthday;
    private UserGenderEnum gender;
    private String bio;
    private MultipartFile file;
}
