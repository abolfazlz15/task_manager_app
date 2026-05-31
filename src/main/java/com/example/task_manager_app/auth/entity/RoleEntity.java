package com.example.task_manager_app.auth.entity;

import com.example.task_manager_app.common.entity.BaseEntity;
import jakarta.persistence.*;

import com.example.task_manager_app.auth.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ElementCollection(targetClass = Permissions.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "permission")
    private Set<Permissions> permissions;
}
