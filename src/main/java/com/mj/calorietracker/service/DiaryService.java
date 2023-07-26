package com.mj.calorietracker.service;

import com.mj.calorietracker.exception.ExistingResourceException;
import com.mj.calorietracker.exception.ResourceNotFoundException;
import com.mj.calorietracker.model.Food;
import com.mj.calorietracker.model.MealDiaryEntries;
import com.mj.calorietracker.model.add.AddDiaryEntryAndFood;
import com.mj.calorietracker.model.add.AddDiaryEntry;
import com.mj.calorietracker.model.DiaryEntry;
import com.mj.calorietracker.repository.DiaryEntryRepository;
import com.mj.calorietracker.repository.FoodRepository;
import com.mj.calorietracker.repository.UnitRepository;
import com.mj.calorietracker.repository.UserRepository;
import com.mj.calorietracker.repository.dao.DiaryEntryEntity;
import com.mj.calorietracker.mapper.DiaryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.mj.calorietracker.enums.ExceptionMessages.*;
import static java.util.stream.Collectors.groupingBy;

@Service
@AllArgsConstructor
public class DiaryService {

    private final FoodService foodService;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final DiaryEntryRepository diaryEntryRepository;
    private final DiaryMapper diaryMapper;

    public final List<DiaryEntry> findAllDiaryEntries() {
        return diaryEntryRepository.findAll().stream().map(diaryMapper::toModel).toList();
    }

    public final List<MealDiaryEntries> findDiaryEntriesByUserIdAndDate(UUID userId, LocalDate entryDate) {
        if (userRepository.findById(userId).isPresent()) {
            return diaryEntryRepository.findAllByUserIdAndEntryDate(userId, entryDate).stream()
                    .collect(groupingBy(DiaryEntryEntity::getMeal))
                    .entrySet().stream()
                    .map(mealListEntry -> new MealDiaryEntries().setMeal(mealListEntry.getKey())
                            .setDiaryEntries(mealListEntry.getValue().stream().map(diaryMapper::toModel).toList()))
                    .toList();
        } else {
            throw new ResourceNotFoundException(USER_NOT_FOUND.getText());
        }
    }

    public final UUID addDiaryEntry(AddDiaryEntry addDiaryEntry) {
        validate(addDiaryEntry);

        return diaryEntryRepository.save(diaryMapper.toEntity(addDiaryEntry)).getId();
    }

    public final UUID addDiaryEntryAndFood(AddDiaryEntryAndFood addDiaryEntryAndFood) {
        UUID foodId;
        try {
            foodId = foodService.addFood(addDiaryEntryAndFood.getFood());
        } catch (ExistingResourceException e) {
            System.out.println(DIARY_FOOD_COULD_NOT_BE_SAVED.getText());
            foodId = ((Food) e.getExistingResource()).getId();
        }

        return diaryEntryRepository.save(diaryMapper.toEntity(addDiaryEntryAndFood, foodId)).getId();
    }

    public final void deleteDiaryEntry(UUID diaryId) {
        diaryEntryRepository.findById(diaryId).ifPresentOrElse(diaryEntryRepository::delete,
                () -> {
                    throw new ResourceNotFoundException(DIARY_ENTRY_NOT_FOUND.getText());
                });
    }

    private void validate(AddDiaryEntry addDiaryEntry) {
        foodRepository.findById(addDiaryEntry.getFoodId()).orElseThrow(() -> new ResourceNotFoundException(FOOD_NOT_FOUND.getText()));
        userRepository.findById(addDiaryEntry.getUserId()).orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.getText()));
        unitRepository.findById(addDiaryEntry.getUnitId()).orElseThrow(() -> new ResourceNotFoundException(UNIT_NOT_FOUND.getText()));
    }

}
