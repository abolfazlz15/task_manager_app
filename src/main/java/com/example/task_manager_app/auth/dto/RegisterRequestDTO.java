package com.example.task_manager_app.auth.dto;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegisterRequestDTO {
    @NotBlank
    @Size(max=50)
    @Email
    private String email;


}
