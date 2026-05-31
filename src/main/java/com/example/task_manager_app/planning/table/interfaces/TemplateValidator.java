package com.example.task_manager_app.planning.table.interfaces;

import com.example.task_manager_app.planning.table.enums.TemplateTypeEnum;

public interface TemplateValidator<T> {
    void validate(T dto);
    TemplateTypeEnum supports();
}