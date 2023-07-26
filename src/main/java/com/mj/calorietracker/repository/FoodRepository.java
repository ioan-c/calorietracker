package com.mj.calorietracker.repository;

import com.mj.calorietracker.repository.dao.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, UUID>, JpaSpecificationExecutor<FoodEntity> {
    Optional<FoodEntity> findByBarcodeAndIsCurrentTrue(String barcode);

    @Modifying
    @Query("UPDATE FoodEntity f SET f.isCurrent = false WHERE f.id = ?1")
    void softDeleteFoodById(UUID id);
}
