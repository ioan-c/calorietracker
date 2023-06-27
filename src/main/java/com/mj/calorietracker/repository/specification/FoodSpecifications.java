package com.mj.calorietracker.repository.specification;

import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

import com.mj.calorietracker.repository.dao.FoodEntity;

public class FoodSpecifications {

    public static Specification<FoodEntity> searchFoods(Set<String> searchValues) {
        return (root, query, criteriaBuilder) -> {
            Predicate[] predicates = searchValues.stream()
                    .map(searchValue -> criteriaBuilder.or(
                            criteriaBuilder.like(root.get("name"), "%" + searchValue + "%"),
                            criteriaBuilder.like(root.get("brand"), "%" + searchValue + "%")
                    ))
                    .toArray(Predicate[]::new);

            return criteriaBuilder.or(predicates);
        };
    }

}
