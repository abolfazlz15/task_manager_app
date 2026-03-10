package com.example.task_manager_app.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RefreshTokenPayloadDTO {
    long userId;
    String ip;
    String location;
    String device;
    String userAgent;

    public RefreshTokenPayloadDTO(long userId , String ip, String device, String userAgent) {
        this(userId, ip, null, device, userAgent);
    }
}
