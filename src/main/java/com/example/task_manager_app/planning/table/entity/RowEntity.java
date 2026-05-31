package com.example.task_manager_app.planning.table.entity;

import com.example.task_manager_app.common.entity.BaseEntity;
import com.example.task_manager_app.planning.table.enums.DataTypeEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "rows")
public class RowEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column
    private Long id;

    @Column(nullable = false)
    private Integer index;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String value;

    @Column(nullable = false)
    private DataTypeEnum dataType;

    @ManyToOne
    @JoinColumn(name = "column_id", nullable = false)
    private ColumnEntity column;
}
