package com.example.task_manager_app.user.service;

import com.example.task_manager_app.common.exception.BadRequestException;
import com.example.task_manager_app.common.service.AesUtil;
import com.example.task_manager_app.user.dto.SessionRefreshTokenPayload;
import com.example.task_manager_app.user.entity.SessionEntity;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.repository.SessionRepository;
import com.example.task_manager_app.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
public class SessionService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final String secret;
    private final String salt;
    private final long expiresInSeconds;
    public SessionService(
            UserRepository userRepository,
            SessionRepository sessionRepository,
            @Value("${session.secret}") String secret,
            @Value("${session.salt}") String salt,
            @Value("${session.expiresIn}") long expiresInSeconds
    ) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.secret = secret;
        this.salt =salt;
        this.expiresInSeconds = expiresInSeconds;
    }

    public String sign(SessionRefreshTokenPayload payload) throws Exception {
        String key = UUID.randomUUID().toString();

        Instant expireAt = Instant.now().plusSeconds(expiresInSeconds);
        UserEntity user = userRepository.findById(payload.getUserId())
                .orElseThrow(() -> new BadRequestException("User not found"));

        SessionEntity session = new SessionEntity();
        session.setUser(user);
        session.setKey(key);
        session.setIp(payload.getIp());
        session.setUserAgent(payload.getUserAgent());
        session.setDevice(payload.getDevice());
        session.setExpireAt(expireAt);

        sessionRepository.save(session);
        byte[] encrypted = AesUtil.encrypt(
                key.getBytes(StandardCharsets.UTF_8),
                secret,
                salt,
                null
        );

        return Base64.getEncoder().encodeToString(encrypted);
    }
}
