package com.example.task_manager_app.planning.table.services;

import com.example.task_manager_app.planning.table.enums.TemplateTypeEnum;
import com.example.task_manager_app.planning.table.interfaces.TemplateValidator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class TemplateValidatorRegistry {

    private final Map<TemplateTypeEnum, TemplateValidator<?>> registry = new HashMap<>();

    public TemplateValidatorRegistry(List<TemplateValidator<?>> validators) {
        for (TemplateValidator<?> v : validators) {
            registry.put(v.supports(), v);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> TemplateValidator<T> getValidator(TemplateTypeEnum type) {
        return (TemplateValidator<T>) registry.get(type);
    }
}