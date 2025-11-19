package com.example.task_manager_app.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshRequest {
    String refreshToken;
}
