package com.example.task_manager_app.common.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @CreationTimestamp
    @Column(name="created_at", updatable=false, nullable=false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name="updated_at", nullable=false)
    private Instant updatedAt;

    @Column(name="deleted_at")
    private Instant deletedAt;

    public void softDelete() {
        this.deletedAt = Instant.now();
    }
}
