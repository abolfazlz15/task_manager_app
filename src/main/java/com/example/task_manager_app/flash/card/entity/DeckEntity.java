package com.example.task_manager_app.flash.card.entity;

import com.example.task_manager_app.common.entity.BaseEntity;
import com.example.task_manager_app.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "decks", uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id", "title"}) })
@SQLDelete(sql = "UPDATE decks SET deleted_at = now() WHERE id=?")
@SQLRestriction("deleted_at IS NULL")
public class DeckEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private Long id;

    @Column(updatable = false)
    private String title;

    @Column
    private String description;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> meta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
