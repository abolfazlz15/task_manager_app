package com.example.task_manager_app.common.dto;

import com.example.task_manager_app.common.constant.IConstant;
import com.example.task_manager_app.user.enums.UserGenderEnum;
import com.example.task_manager_app.user.enums.UserStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class FilterRequest {
    private long id;
    private String username;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private UserStatusEnum status;
    private UserGenderEnum gender;
    private Set<String> roleNames;
    private int size = IConstant.DEFAULT_PAGE_SIZE;
}
