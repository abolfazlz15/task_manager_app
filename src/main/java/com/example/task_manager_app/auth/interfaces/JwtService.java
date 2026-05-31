package com.example.task_manager_app.auth.interfaces;

import java.util.Map;

import io.jsonwebtoken.Claims;

public interface JwtService {
    public String generateToken(Map<String, Object> claims, String subject);
    public Claims parseToken(String token);
}
