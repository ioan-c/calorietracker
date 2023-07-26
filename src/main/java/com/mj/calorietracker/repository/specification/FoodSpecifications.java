package com.mj.calorietracker.repository.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.Set;

import com.mj.calorietracker.repository.dao.FoodEntity;

public class FoodSpecifications {
    public static Specification<FoodEntity> searchInFoods(Set<String> searchValues) {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = searchValues.stream()
                    .map(searchValue -> criteriaBuilder.or(
                            criteriaBuilder.like(root.get("name"), "%" + searchValue + "%"),
                            criteriaBuilder.like(root.get("brand"), "%" + searchValue + "%")
                    ))
                    .toArray(Predicate[]::new);
            Predicate isCurrent = isCurrent(root, criteriaBuilder);

            return criteriaBuilder.and(isCurrent, criteriaBuilder.or(predicates));
        };
    }

    public static Specification<FoodEntity> findIfItWillDuplicate(String name, String brand) {
        return (root, query, criteriaBuilder) -> {
            Predicate nameEquals = nameEquals(name, root, criteriaBuilder);
            Predicate brandEquals = brandEquals(brand, root, criteriaBuilder);
            Predicate isCurrent = isCurrent(root, criteriaBuilder);

            return criteriaBuilder.and(nameEquals, brandEquals, isCurrent);
        };
    }

    private static Predicate nameEquals(String name, Root<FoodEntity> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(criteriaBuilder.lower(root.get("name")), name.toLowerCase());
    }

    private static Predicate brandEquals(String brand, Root<FoodEntity> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(brand)
                .map(value -> criteriaBuilder.equal(criteriaBuilder.lower(root.get("brand")), brand.toLowerCase()))
                .orElse(criteriaBuilder.isNull(root.get("brand")));
    }

    private static Predicate isCurrent(Root<FoodEntity> root, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get("isCurrent"), true);
    }
}
