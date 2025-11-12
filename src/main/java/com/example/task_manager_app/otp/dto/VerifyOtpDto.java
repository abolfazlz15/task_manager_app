package com.example.task_manager_app.otp.dto;

import com.example.task_manager_app.otp.enums.OtpOperationTypeEnum;
import com.example.task_manager_app.otp.enums.OtpTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerifyOtpDto {
    private String id;
    private String code;
    private OtpTypeEnum type;
    private OtpOperationTypeEnum operation;

}