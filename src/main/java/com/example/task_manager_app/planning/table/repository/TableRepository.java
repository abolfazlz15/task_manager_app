package com.example.task_manager_app.planning.table.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.task_manager_app.planning.table.entity.TableEntity;

import java.util.Optional;


@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long>, JpaSpecificationExecutor<TableEntity> {

    @Query("""
        SELECT DISTINCT t
        FROM TableEntity t
        JOIN FETCH t.columns c
        JOIN FETCH c.rows r
        WHERE t.user.id = :userId and t.id = :tableId
    """)
    Optional<TableEntity> findTableWithColumnsAndRows(@Param("userId") Long userId, @Param("tableId") Long tableId);

    @Query("""
        SELECT
            (SUM(CASE WHEN r.value = 'true' THEN 1 ELSE 0 END) * 100.0)
            / COUNT(r)
        FROM TableEntity t
        JOIN t.columns c
        JOIN c.rows r
        WHERE t.id = :tableId
          AND r.dataType = com.example.task_manager_app.planning.table.enums.DataTypeEnum.BOOLEAN
    """)
    Double calculateAverage(@Param("tableId") Long tableId);

    @Query("""
        SELECT t FROM TableEntity t WHERE t.user.id = :userId and t.id = :tableId
    """)
    Optional<TableEntity> findTableByUserIdAndTableId(@Param("userId") Long userId, @Param("tableId") Long tableId);

    boolean existsByUserIdAndId(Long userId, Long id);

}