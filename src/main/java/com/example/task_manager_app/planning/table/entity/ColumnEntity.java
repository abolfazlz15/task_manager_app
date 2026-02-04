package com.example.task_manager_app.planning.table.entity;

import com.example.task_manager_app.common.entity.BaseEntity;
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
@Table(name = "columns")
public class ColumnEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer index;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> meta;

    @ManyToOne
    @JoinColumn(name="table_id", nullable=false)
    private TableEntity table;

    @OneToMany(mappedBy = "column", cascade = CascadeType.ALL)
    private List<RowEntity> rows;
}
