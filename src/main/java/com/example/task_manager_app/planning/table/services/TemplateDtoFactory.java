package com.example.task_manager_app.planning.table.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TemplateDtoFactory {
    private final ObjectMapper mapper;

    public <T> T convert(Map<String, Object> payload, Class<T> targetType) {
        return mapper.convertValue(payload, targetType);
    }
}
