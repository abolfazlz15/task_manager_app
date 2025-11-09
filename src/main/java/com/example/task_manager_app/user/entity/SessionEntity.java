package com.example.task_manager_app.user.entity;

import java.time.Instant;
import java.util.Map;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="sessions")
public class SessionEntity {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable=false, unique=true)
    private String key;

    @Column(nullable=false, name="expire_at")
    private Instant expireAt;
    
    @Column(nullable=false)
    private String ip;
    
    @Column(name="user_agent")
    private String userAgent;

    @Column
    private String device;
    
    @Column
    private String location;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> meta;

    @Column(name="created_at")
    @CreationTimestamp
    private Instant createdAt;
    
    @Column(name="updated_at")
    @UpdateTimestamp
    private Instant updatedAt;
    
    @Column(name="deleted_at")
    private Instant deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    private UserEntity user;
}
