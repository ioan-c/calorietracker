package com.mj.calorietracker.repository;

import com.mj.calorietracker.repository.dao.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FoodRepository extends JpaRepository<FoodEntity, UUID> {

    Optional<FoodEntity> findByBarcode(String barcode);
}
