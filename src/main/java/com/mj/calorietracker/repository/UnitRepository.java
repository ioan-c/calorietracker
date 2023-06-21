package com.mj.calorietracker.repository;

import com.mj.calorietracker.repository.dao.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UnitRepository extends JpaRepository<UnitEntity, UUID> {
}
