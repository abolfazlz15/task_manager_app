package com.example.task_manager_app.user.interfaces;

import com.example.task_manager_app.user.dto.SessionRefreshTokenPayload;
import com.example.task_manager_app.user.entity.SessionEntity;

public interface SessionService {
    public String sign(SessionRefreshTokenPayload payload) throws Exception;
    public SessionEntity verify(String refreshToken) throws Exception;
}
