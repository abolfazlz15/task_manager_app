package com.example.task_manager_app.flash.card.entity;

import com.example.task_manager_app.common.entity.BaseEntity;
import com.example.task_manager_app.flash.card.enums.CardTypeEnum;
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
@Table(name = "cards")
@SQLDelete(sql = "UPDATE cards SET deleted_at = now() WHERE id=?")
@SQLRestriction("deleted_at IS NULL")
public class CardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private Long id;

    @Column(nullable = false)
    private String front;

    @Column(nullable = false)
    private String back;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardTypeEnum type =  CardTypeEnum.BASIC;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> meta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id", nullable = false)
    private DeckEntity deck;

    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL)
    private ReviewEntity review;
}
