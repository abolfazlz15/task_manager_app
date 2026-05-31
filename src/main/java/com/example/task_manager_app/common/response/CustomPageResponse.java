package com.example.task_manager_app.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomPageResponse<T> {
    private List<T> content;
    private long totalElements;
    private int pageSize;
    private int pageNumber;
    private int totalPages;

    public static <T> CustomPageResponse<T> fromPage(Page<T> page) {
        return CustomPageResponse.<T>builder()
                .content(page.getContent())
                .totalElements(page.getTotalElements())
                .pageSize(page.getSize())
                .pageNumber(page.getNumber())
                .totalPages(page.getTotalPages())
                .build();
    }
}