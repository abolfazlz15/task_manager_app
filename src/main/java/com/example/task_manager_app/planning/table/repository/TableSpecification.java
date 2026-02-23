package com.example.task_manager_app.planning.table.repository;

import com.example.task_manager_app.planning.table.dto.TableFilter;
import com.example.task_manager_app.planning.table.entity.TableEntity;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.util.StringUtils;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class TableSpecification {

    public static Specification<TableEntity> getTablesByFilter(TableFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.isNull(root.get("deletedAt")));

            if (filter == null) {
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }

            if (StringUtils.hasText(filter.getName())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + filter.getName().toLowerCase() + "%"
                ));
            }

            if (filter.getCreatedAfter() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("createdAt"),
                        filter.getCreatedAfter().toInstant(ZoneOffset.UTC)
                ));
            }
            if (filter.getCreatedBefore() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("createdAt"),
                        filter.getCreatedBefore().toInstant(ZoneOffset.UTC)
                ));
            }

            if (filter.getUpdatedAfter() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("updatedAt"),
                        filter.getUpdatedAfter().toInstant(ZoneOffset.UTC)
                ));
            }

            if (filter.getUpdatedBefore() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("updatedAt"),
                        filter.getUpdatedBefore().toInstant(ZoneOffset.UTC)
                ));
            }

            if (filter.getUserId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("user").get("id"),
                        filter.getUserId()
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

