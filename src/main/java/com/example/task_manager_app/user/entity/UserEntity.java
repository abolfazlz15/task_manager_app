package com.example.task_manager_app.user.entity;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import com.example.task_manager_app.user.enums.UserGenderEnum;
import com.example.task_manager_app.user.enums.UserRoleEnum;
import com.example.task_manager_app.user.enums.UserStatusEnum;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column
    private long id;

    @Column(name="first_name", unique=true)
    private String username;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="password")
    private String password;

    @Column(name="phone")
    private String phone;

    @Column(name="password_update_at")
    @CreationTimestamp
    private Instant passwordUpdateAt;

    @Column(name="birthday")
    @CreationTimestamp
    private Instant birthday;

    @Column(name="gender")
    private UserGenderEnum gender;

    @Column(name="bio")
    private String bio;

    @Column(name="profile_image")
    private String profileImage;

    @Column(name="is_phone_verified")
    private String isPhoneVerified;

    @Column(name="is_email_verified")
    private String isEmailVerified;

    @Column(name="status")
    private UserStatusEnum status;

    @Column(name="role")
    private UserRoleEnum role;

    @Column(columnDefinition = "jsonb")
    private JsonNode meta;

    @Column(name="created_at")
    @CreationTimestamp
    private Instant createdAt;

    @Column(name="updated_at")
    @CreationTimestamp
    private Instant updatedAt;
}
