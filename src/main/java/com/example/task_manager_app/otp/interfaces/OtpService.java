package com.example.task_manager_app.otp.interfaces;

import com.example.task_manager_app.otp.dto.CreateOtpDto;
import com.example.task_manager_app.otp.dto.VerifyOtpDto;

public interface OtpService {
    String createOtp(CreateOtpDto createOtpDto);
    void verifyOtp(VerifyOtpDto verifyOtpDto);
}
