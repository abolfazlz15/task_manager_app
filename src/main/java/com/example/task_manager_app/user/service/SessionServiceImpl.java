package com.example.task_manager_app.user.service;

import java.time.Instant;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Service;

import com.example.task_manager_app.common.exception.BadRequestException;
import com.example.task_manager_app.common.service.AesUtil;
import com.example.task_manager_app.user.dto.SessionRefreshTokenPayload;
import com.example.task_manager_app.user.entity.SessionEntity;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.interfaces.SessionService;
import com.example.task_manager_app.user.repository.SessionRepository;
import com.example.task_manager_app.user.repository.UserRepository;

@Service
public class SessionServiceImpl implements SessionService {
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final String secret;
    private final String salt;
    private final long expiresInSeconds;

    public SessionServiceImpl(
            UserRepository userRepository,
            SessionRepository sessionRepository,
            @Value("${session.secret}") String secret,
            @Value("${session.salt}") String salt,
            @Value("${session.expiresIn}") long expiresInSeconds
    ) {
        this.sessionRepository = sessionRepository;
        this.userRepository = userRepository;
        this.secret = secret;
        this.salt = salt;
        this.expiresInSeconds = expiresInSeconds;
    }

    public String sign(SessionRefreshTokenPayload payload) throws Exception {
        byte[] keyBytes = KeyGenerators.secureRandom(16).generateKey();
        String key = Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes);

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
                keyBytes,
                secret,
                salt,
                null
        );

        return Base64.getEncoder().encodeToString(encrypted);
    }

    @Override
    public SessionEntity verify(String refreshToken) throws Exception {
        byte[] encrypted = Base64.getDecoder().decode(refreshToken);
        byte[] keyBytes = AesUtil.decrypt(encrypted, secret, salt);

        String key = Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes);
        Optional<SessionEntity> session = Optional.of(sessionRepository.findByKey(key)
                .orElseThrow(() -> new BadRequestException("session not found")));

        if (session.isEmpty() || session.get().getExpireAt().isBefore(Instant.now())) {
            throw new BadRequestException("invalid or expired session");
        }
        return session.get();
    }
}