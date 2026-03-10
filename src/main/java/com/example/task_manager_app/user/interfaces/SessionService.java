package com.example.task_manager_app.user.interfaces;

import com.example.task_manager_app.user.dto.RefreshTokenPayloadDTO;
import com.example.task_manager_app.user.entity.SessionEntity;

public interface SessionService {
    public String sign(RefreshTokenPayloadDTO payload) throws Exception;
    public SessionEntity verify(String refreshToken) throws Exception;
    public boolean revoke(long userId, String token) throws Exception;
    void purgeUserActiveTokens(long userId);
}
