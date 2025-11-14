package com.example.task_manager_app.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerifyRegisterResponse {
    String accessToken;
    String refreshToken;
}
