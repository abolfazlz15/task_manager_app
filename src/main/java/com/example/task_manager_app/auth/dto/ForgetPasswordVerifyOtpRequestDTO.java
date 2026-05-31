package com.example.task_manager_app.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgetPasswordVerifyOtpRequestDTO extends  BaseForgetPasswordRequestDTO {
    @NotNull
    private String otpCode;

}
