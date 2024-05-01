package com.mj.calorietracker.repository;

import com.mj.calorietracker.repository.dao.DiaryEntryEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiaryEntryRepository extends AppRepository<DiaryEntryEntity, Integer> {

    List<DiaryEntryEntity> findAllByUserIdAndEntryDate (String userId, LocalDate entryDate);
}
