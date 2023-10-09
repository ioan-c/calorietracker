package com.mj.calorietracker.repository;

import com.mj.calorietracker.repository.dao.FoodUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FoodUnitRepository extends JpaRepository<FoodUnitEntity, Integer> {
    List<FoodUnitEntity> findAllByFoodIdIsNullOrFoodId(UUID foodId);
}
