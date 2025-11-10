package com.example.task_manager_app.otp.interfaces;

import com.example.task_manager_app.otp.dto.CreateOtpDto;

public interface OtpService {
    String createOtp(CreateOtpDto createOtpDto);
}
