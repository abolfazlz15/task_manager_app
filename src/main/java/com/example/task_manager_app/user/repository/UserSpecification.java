package com.example.task_manager_app.user.repository;

import com.example.task_manager_app.auth.entity.RoleEntity;
import com.example.task_manager_app.common.dto.FilterRequest;
import com.example.task_manager_app.user.entity.UserEntity;
import com.example.task_manager_app.user.enums.PrivilegeEnum;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class UserSpecification {

    public static Specification<UserEntity> filterBy(FilterRequest filter, PrivilegeEnum privilege) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (privilege.equals(PrivilegeEnum.USER)) {
                predicates.add(cb.equal(root.get("privilege"), privilege));
            } else {
                predicates.add(cb.notEqual(root.get("privilege"), PrivilegeEnum.USER));

                if (filter.getRoleNames() != null && !filter.getRoleNames().isEmpty()) {
                    Join<UserEntity, RoleEntity> rolesJoin = root.join("roles", JoinType.LEFT);
                    predicates.add(rolesJoin.get("name").in(filter.getRoleNames()));
                }
            }

            if (filter.getId() > 0) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }

            addLikePredicate(cb, root, predicates, "username", filter.getUsername());
            addLikePredicate(cb, root, predicates, "email", filter.getEmail());
            addLikePredicate(cb, root, predicates, "phone", filter.getPhone());
            addLikePredicate(cb, root, predicates, "firstName", filter.getFirstName());
            addLikePredicate(cb, root, predicates, "lastName", filter.getLastName());

            if (filter.getStatus() != null) {
                predicates.add(cb.equal(root.get("status"), filter.getStatus()));
            }
            if (filter.getGender() != null) {
                predicates.add(cb.equal(root.get("gender"), filter.getGender()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static void addLikePredicate(CriteriaBuilder cb, Root<UserEntity> root,
                                         List<Predicate> predicates, String fieldName, String value) {
        if (value != null && !value.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get(fieldName)), "%" + value.toLowerCase() + "%"));
        }
    }
}
