package com.example.task_manager_app.planning.table.interfaces;

import com.example.task_manager_app.planning.table.entity.TableEntity;
import com.example.task_manager_app.planning.table.enums.TemplateTypeEnum;

public interface TemplateAssembler<T> {
    TableEntity assembleTable(TableEntity base, T dto);
    TemplateTypeEnum supports();
}
