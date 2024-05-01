package com.mj.calorietracker.repository;

import com.mj.calorietracker.repository.dao.FoodEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodRepository extends AppRepository<FoodEntity, Integer>, JpaSpecificationExecutor<FoodEntity> {
    Optional<FoodEntity> findByBarcodeAndIsCurrentTrue(String barcode);
    Optional<FoodEntity> findByNameEqualsIgnoreCaseAndBrandIsNullAndIsCurrentTrue(String name);
    @Modifying
    @Query("UPDATE FoodEntity f SET f.isCurrent = false WHERE f.id = ?1")
    void softDeleteFoodById(Integer id);
    @Modifying
    @Query("UPDATE FoodEntity f SET f.name = ?2 WHERE f.id = ?1")
    void updateFoodName(Integer id, String newName);
}
