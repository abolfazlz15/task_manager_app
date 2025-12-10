package com.example.task_manager_app.planning.table.entity;

import com.example.task_manager_app.common.entity.BaseEntity;
import com.example.task_manager_app.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "tables")
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

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity user;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
    private List<ColumnEntity> columns;
}
