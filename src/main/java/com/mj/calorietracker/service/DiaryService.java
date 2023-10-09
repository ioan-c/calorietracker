package com.mj.calorietracker.service;

import com.mj.calorietracker.dto.DiaryEntry;
import com.mj.calorietracker.dto.LocalResourceBridge;
import com.mj.calorietracker.dto.MealDiaryEntries;
import com.mj.calorietracker.dto.add.AddDiaryEntry;
import com.mj.calorietracker.dto.add.AddLocalDiaryEntry;
import com.mj.calorietracker.exception.ResourceNotFoundException;
import com.mj.calorietracker.exception.ResourcesNotFoundException;
import com.mj.calorietracker.exception.model.ErrorInfoForList;
import com.mj.calorietracker.repository.DiaryEntryRepository;
import com.mj.calorietracker.repository.FoodRepository;
import com.mj.calorietracker.repository.FoodUnitRepository;
import com.mj.calorietracker.repository.UserRepository;
import com.mj.calorietracker.repository.dao.DiaryEntryEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;

import static com.mj.calorietracker.enums.ExceptionMessages.*;
import static com.mj.calorietracker.mapper.DiaryMapper.diaryMapper;
import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class DiaryService {

    private FoodService foodService;
    private FoodRepository foodRepository;
    private UserRepository userRepository;
    private FoodUnitRepository foodUnitRepository;
    private DiaryEntryRepository diaryEntryRepository;

    public List<DiaryEntry> findAllDiaryEntries() {
        return diaryEntryRepository.findAll().stream().map(diaryMapper::toDto).toList();
    }

    public List<MealDiaryEntries> findDiaryEntriesByUserIdAndDate(UUID userId, LocalDate entryDate) {
        if (userRepository.findById(userId).isPresent()) {
            return diaryEntryRepository.findAllByUserIdAndEntryDate(userId, entryDate).stream()
                    .collect(groupingBy(DiaryEntryEntity::getMeal))
                    .entrySet().stream()
                    .map(mealListEntry -> new MealDiaryEntries().setMeal(mealListEntry.getKey())
                            .setDiaryEntries(mealListEntry.getValue().stream().map(diaryMapper::toDto).toList()))
                    .toList();
        } else {
            throw new ResourceNotFoundException(USER_NOT_FOUND.getText());
        }
    }

    public UUID addDiaryEntry(AddDiaryEntry addDiaryEntry) {
        validate(addDiaryEntry);

        return diaryEntryRepository.save(diaryMapper.toEntity(addDiaryEntry)).getId();
    }

    public List<LocalResourceBridge> addDiaryEntriesList(List<AddLocalDiaryEntry> addDiaryEntryList) {
        validateDiaryEntries(addDiaryEntryList);

        return addDiaryEntryList.stream()
                .map(diaryEntry -> {
                    UUID diaryEntryId = diaryEntryRepository.save(diaryMapper.toEntity(diaryEntry)).getId();
                    return new LocalResourceBridge(diaryEntryId, diaryEntry.getLocalId());
                }).toList();
    }

    private void validateDiaryEntries(List<AddLocalDiaryEntry> addDiaryEntryList) {
        List<ErrorInfoForList> errorInfoList = IntStream.range(0, addDiaryEntryList.size())
                .mapToObj(i -> {
                    AddLocalDiaryEntry addEntry = addDiaryEntryList.get(i);
                    try {
                        validate(addEntry);
                        return null;
                    } catch (ResourceNotFoundException ex) {
                        return new ErrorInfoForList(ex.getMessage(), addEntry.getLocalId(), i);
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        digestErrorList(errorInfoList);
    }

    private void digestErrorList(List<ErrorInfoForList> errorInfoList) {
        if(!CollectionUtils.isEmpty(errorInfoList)) {
           throw new ResourcesNotFoundException(errorInfoList);
        }
    }

    public void deleteDiaryEntry(UUID diaryId) {
        diaryEntryRepository.findById(diaryId).ifPresentOrElse(diaryEntryRepository::delete,
                () -> {
                    throw new ResourceNotFoundException(DIARY_ENTRY_NOT_FOUND.getText());
                });
    }

    public void deleteDiaryEntryBatch(List<UUID> diaryEntryIds) {
        diaryEntryRepository.deleteAllByIdInBatch(diaryEntryIds);
    }

    private void validate(AddDiaryEntry addDiaryEntry) {
        validateFoodExists(addDiaryEntry.getFoodId());
        validateUserExists(addDiaryEntry.getUserId());
        validateUnitAvailableForFood(addDiaryEntry.getFoodId(), addDiaryEntry.getFoodUnitId());
    }

    private void validateFoodExists(UUID foodId) {
        foodRepository.findById(foodId)
                .orElseThrow(() -> new ResourceNotFoundException(FOOD_NOT_FOUND.getText()));
    }

    private void validateUserExists(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.getText()));
    }

    private void validateUnitAvailableForFood(UUID foodId, Integer foodUnitId) {
        foodUnitRepository.findAllByFoodIdIsNullOrFoodId(foodId).stream()
                .filter(fu -> fu.getId().equals(foodUnitId))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException(UNIT_NOT_FOUND.getText()));
    }
}
