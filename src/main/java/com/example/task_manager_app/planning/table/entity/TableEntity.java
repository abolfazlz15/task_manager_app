package com.example.task_manager_app.planning.table.entity;

import com.example.task_manager_app.common.entity.BaseEntity;
import com.example.task_manager_app.planning.table.enums.TemplateTypeEnum;
import com.example.task_manager_app.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;
import java.util.Map;
import java.util.Set;


@Getter
@Setter
@Entity
@Table(
        name = "tables",
        indexes = { @Index(name = "idx_tables_user_id_id", columnList = "user_id, id") }
)
@SQLDelete(sql = "UPDATE tables SET deleted_at = now() WHERE id=?")
@SQLRestriction("deleted_at IS NULL")
public class TableEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> meta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity user;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    private Set<ColumnEntity> columns;

    @Column
    private Short score = 0;

    @Column(name = "template_type")
    @Enumerated(EnumType.STRING)
    private TemplateTypeEnum templateType;
}
