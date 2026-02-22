package com.example.task_manager_app.user.entity;
import java.time.Instant;
import java.util.Map;
import java.util.Set;

import com.example.task_manager_app.user.enums.PrivilegeEnum;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.example.task_manager_app.auth.entity.RoleEntity;
import com.example.task_manager_app.common.entity.BaseEntity;
import com.example.task_manager_app.user.enums.UserGenderEnum;
import com.example.task_manager_app.user.enums.UserStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column
    private Long id;

    @Column(unique=true, nullable=false)
    private String email;

    @Column(unique=true, nullable=false)
    private String username;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column
    @JsonIgnore
    private String password;
    
    @Column(name="password_update_at")
    private Instant passwordUpdateAt;

    @Column
    private String phone;

    @Column
    private Instant birthday;

    @Column
    @Enumerated(EnumType.STRING)
    private UserGenderEnum gender;

    @Column
    private String bio;

    @Column(name="profile_image")
    private String profileImage;

    @Column(name="is_phone_verified")
    private boolean isPhoneVerified;

    @Column(name="is_email_verified")
    private boolean isEmailVerified;

    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    @Column(nullable = false, name = "privilege")
    @Enumerated(EnumType.STRING)
    private PrivilegeEnum privilege = PrivilegeEnum.USER;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> meta;
}
