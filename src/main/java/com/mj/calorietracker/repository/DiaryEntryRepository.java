package com.mj.calorietracker.repository;

import com.mj.calorietracker.repository.dao.DiaryEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DiaryEntryRepository extends JpaRepository<DiaryEntryEntity, UUID> {

    List<DiaryEntryEntity> findAllByUserIdAndEntryDate (UUID userId, LocalDate entryDate);
}
