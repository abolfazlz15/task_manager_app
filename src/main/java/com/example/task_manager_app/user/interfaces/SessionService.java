package com.example.task_manager_app.user.interfaces;

import com.example.task_manager_app.user.dto.SessionRefreshTokenPayload;

public interface SessionService {
    public String sign(SessionRefreshTokenPayload payload) throws Exception;
    public boolean revoke(long userId, String token) throws Exception;
}
