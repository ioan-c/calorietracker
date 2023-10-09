package com.mj.calorietracker.repository;

import com.mj.calorietracker.repository.dao.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, String> {
}
