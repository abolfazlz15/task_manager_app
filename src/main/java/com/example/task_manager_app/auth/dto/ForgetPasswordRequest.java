package com.example.task_manager_app.auth.dto;

import com.example.task_manager_app.common.exception.BadRequestException;
import com.example.task_manager_app.otp.enums.OtpTypeEnum;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgetPasswordRequest {
    @NotNull(message = "otpType is required")
    private OtpTypeEnum otpType;

    @NotNull(message = "test")
    private String token;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "\\+?[0-9]{10,15}", message = "Invalid phone number")
    private String cellphone;

    @NotBlank
    @Size(min=6, max=40)
    private String password;

    public void validate() {
        if (getOtpType() == OtpTypeEnum.EMAIL && (email == null || email.isBlank())) {
            throw new BadRequestException("Email is required for otpType EMAIL");
        }
        if (getOtpType() == OtpTypeEnum.SMS && (cellphone == null || cellphone.isBlank())) {
            throw new BadRequestException("Cellphone is required for otpType SMS");
        }
    }
}
