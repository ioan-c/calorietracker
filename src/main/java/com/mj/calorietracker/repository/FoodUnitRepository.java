package com.mj.calorietracker.repository;

import com.mj.calorietracker.repository.dao.FoodUnitEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodUnitRepository extends AppRepository<FoodUnitEntity, Integer> {
    List<FoodUnitEntity> findAllByFoodIdIsNullOrFoodId(Integer foodId);

    @Query("select case when count(fue) > 0 then true else false end from FoodUnitEntity fue" +
            " where (fue.foodId = :foodId or fue.foodId is null)" +
            " and fue.id = :foodUnitId")
    boolean existsForFood(Integer foodId, Integer foodUnitId);
}
