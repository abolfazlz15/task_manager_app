package com.example.task_manager_app.otp.dto;

import com.example.task_manager_app.otp.enums.OtpOprationTypeEnum;
import com.example.task_manager_app.otp.enums.OtpTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateOtpDto {
    private String id;
    private OtpTypeEnum type;
    private OtpOprationTypeEnum operation;
    private Long ttl;
    private Integer timeout;

    public CreateOtpDto(String id, OtpTypeEnum type, OtpOprationTypeEnum operation) {
        this(id, type, operation, null, null);
    }
}