package com.example.task_manager_app.user.dto;

import com.example.task_manager_app.user.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SessionRefreshTokenPayload {
    long userId;
    UserRoleEnum role;
    String ip;
    String location;
    String device;
    String userAgent;

    public SessionRefreshTokenPayload(long userId, UserRoleEnum role, String ip, String device, String userAgent) {
        this(userId, role, ip, null, device, userAgent);
    }
}
