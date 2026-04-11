package com.example.task_manager_app.planning.table.services;

import com.example.task_manager_app.planning.table.enums.TemplateTypeEnum;
import com.example.task_manager_app.planning.table.interfaces.TemplateAssembler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TemplateAssemblerRegistry {

    private final Map<TemplateTypeEnum, TemplateAssembler<?>> map = new HashMap<>();

    public TemplateAssemblerRegistry(List<TemplateAssembler<?>> assemblers) {
        for (TemplateAssembler<?> a : assemblers) {
            map.put(a.supports(), a);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> TemplateAssembler<T> get(TemplateTypeEnum type) {
        return (TemplateAssembler<T>) map.get(type);
    }
}
