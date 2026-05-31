package com.example.task_manager_app.flash.card.entity;

import com.example.task_manager_app.common.entity.BaseEntity;
import com.example.task_manager_app.flash.card.enums.ReviewStateEnum;
import com.example.task_manager_app.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "reviews")
@AllArgsConstructor
@NoArgsConstructor
public class ReviewEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewStateEnum state;

    @Column(name = "next_due")
    private Instant nextDue = Instant.now();

    @Column(name = "last_review")
    private Instant lastReview;

    @Column(nullable = false)
    private Integer interval = 0;

    @Column(name="ease_factor", nullable = false)
    private Double easeFactor = 2.5;

    @Column(nullable = false)
    private Integer repetition = 0;

    @Column(nullable = false)
    private Integer lapses = 0;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> meta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "card_id", nullable = false)
    private CardEntity card;
}
