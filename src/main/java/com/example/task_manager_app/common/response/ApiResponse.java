package com.example.task_manager_app.common.response;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private T data;
    private ErrorResponse error;

    public ApiResponse(T data, ErrorResponse error) {
        this.data = data;
        this.error = error;
    }
}