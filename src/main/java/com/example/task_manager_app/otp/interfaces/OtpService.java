package com.example.task_manager_app.otp.interfaces;

import com.example.task_manager_app.otp.dto.CreateOtpDTO;
import com.example.task_manager_app.otp.dto.VerifyOtpDTO;

public interface OtpService {
    String createOtp(CreateOtpDTO createOtpDTO);
    void verifyOtp(VerifyOtpDTO verifyOtpDto);
}
