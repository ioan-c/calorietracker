package com.mj.calorietracker.service;

import com.mj.calorietracker.dto.DiaryEntry;
import com.mj.calorietracker.dto.LocalResourceBridge;
import com.mj.calorietracker.dto.MealDiaryEntries;
import com.mj.calorietracker.dto.add.AddDiaryEntry;
import com.mj.calorietracker.dto.add.AddLocalDiaryEntry;
import com.mj.calorietracker.exception.ResourceNotFoundException;
import com.mj.calorietracker.repository.DiaryEntryRepository;
import com.mj.calorietracker.repository.dao.DiaryEntryEntity;
import com.mj.calorietracker.service.validator.DiaryValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.mj.calorietracker.enums.ExceptionMessages.DIARY_ENTRY_NOT_FOUND;
import static com.mj.calorietracker.mapper.DiaryMapper.diaryMapper;
import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class DiaryService {

    private DiaryEntryRepository diaryEntryRepository;
    private DiaryValidator validator;

    public List<DiaryEntry> findAllDiaryEntries() {
        return diaryEntryRepository.findAll().stream().map(diaryMapper::toDto).toList();
    }

    public List<MealDiaryEntries> findDiaryEntriesByUserIdAndDate(String userId, LocalDate entryDate) {
        validator.validateUserExists(userId);
        return diaryEntryRepository.findAllByUserIdAndEntryDate(userId, entryDate).stream()
                .collect(groupingBy(DiaryEntryEntity::getMeal))
                .entrySet().stream()
                .map(mealListEntry -> new MealDiaryEntries().setMeal(mealListEntry.getKey())
                        .setDiaryEntries(mealListEntry.getValue().stream().map(diaryMapper::toDto).toList()))
                .toList();
    }

    public Integer addDiaryEntry(AddDiaryEntry addDiaryEntry) {
        validator.validateDiaryEntry(addDiaryEntry);

        return diaryEntryRepository.save(diaryMapper.toEntity(addDiaryEntry)).getId();
    }

    public List<LocalResourceBridge> addDiaryEntriesList(List<AddLocalDiaryEntry> addDiaryEntryList) {
        validator.validateDiaryEntries(addDiaryEntryList);

        return addDiaryEntryList.stream()
                .map(diaryEntry -> {
                    Integer diaryEntryId = diaryEntryRepository.save(diaryMapper.toEntity(diaryEntry)).getId();
                    return new LocalResourceBridge(diaryEntryId, diaryEntry.getLocalId());
                }).toList();
    }

    public void deleteDiaryEntry(Integer diaryId) {
        diaryEntryRepository.findById(diaryId).ifPresentOrElse(diaryEntryRepository::delete,
                () -> {
                    throw new ResourceNotFoundException(DIARY_ENTRY_NOT_FOUND.getText());
                });
    }

    public void deleteDiaryEntryBatch(List<Integer> diaryEntryIds) {
        diaryEntryRepository.deleteAllByIdInBatch(diaryEntryIds);
    }
}
