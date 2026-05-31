package com.example.task_manager_app.otp.dto;

import com.example.task_manager_app.otp.enums.OtpOperationTypeEnum;
import com.example.task_manager_app.otp.enums.OtpTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateOtpDTO {
    private String id;
    private OtpTypeEnum type;
    private OtpOperationTypeEnum operation;
    private Long ttl;
    private Integer timeout;

    public CreateOtpDTO(String id, OtpTypeEnum type, OtpOperationTypeEnum operation) {
        this(id, type, operation, null, null);
    }
}