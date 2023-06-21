package com.mj.calorietracker.service;

import com.mj.calorietracker.exception.FoodAlreadyExistsException;
import com.mj.calorietracker.exception.ResourceNotFoundException;
import com.mj.calorietracker.mapper.FoodMapper;
import com.mj.calorietracker.model.MealDiaryEntries;
import com.mj.calorietracker.model.NutritionInfo;
import com.mj.calorietracker.model.add.AddDiaryEntryAndFood;
import com.mj.calorietracker.model.add.AddDiaryEntry;
import com.mj.calorietracker.model.DiaryEntry;
import com.mj.calorietracker.repository.DiaryEntryRepository;
import com.mj.calorietracker.repository.FoodRepository;
import com.mj.calorietracker.repository.UnitRepository;
import com.mj.calorietracker.repository.UserRepository;
import com.mj.calorietracker.repository.dao.DiaryEntryEntity;
import com.mj.calorietracker.mapper.DiaryMapper;
import com.mj.calorietracker.repository.dao.FoodEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.mj.calorietracker.enums.ExceptionMessages.*;
import static com.mj.calorietracker.util.NutritionValueConvertor.convertToServingQuantity;
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
    private final FoodMapper foodMapper;

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
                    .collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException(USER_NOT_FOUND.getMessage());
        }
    }

    public final void addDiaryEntry(AddDiaryEntry addDiaryEntry) {
        Optional<FoodEntity> foodEntityOptional = foodRepository.findById(addDiaryEntry.getFoodId());
        validate(addDiaryEntry, foodEntityOptional);

        FoodEntity foodEntity = foodEntityOptional.get();
        NutritionInfo foodNutritionalInfo = foodMapper.getNutritionalInfo(foodEntity);
        DiaryEntryEntity diaryEntryEntity = diaryMapper.toEntity(addDiaryEntry, foodEntity.getName(), foodEntity.getBrand(), convertToServingQuantity(foodNutritionalInfo, addDiaryEntry.getServingQuantity()));
        diaryEntryRepository.save(diaryEntryEntity);

    }

    public final void addDiaryEntryAndFood(AddDiaryEntryAndFood addDiaryEntryAndFood) {
        try {
            foodService.addFood(foodMapper.toModel(addDiaryEntryAndFood));
        } catch (FoodAlreadyExistsException e) {
            System.out.println(DIARY_FOOD_COULD_NOT_BE_SAVED.getMessage());
        }
        AddDiaryEntryAndFood convertedToServingQuantity = addDiaryEntryAndFood.setNutritionInfo(convertToServingQuantity(addDiaryEntryAndFood.getNutritionInfo(), addDiaryEntryAndFood.getServingQuantity()));

        DiaryEntryEntity diaryEntryEntity = diaryMapper.toEntity(convertedToServingQuantity);
        diaryEntryRepository.save(diaryEntryEntity);
    }

    public final void deleteDiaryEntry(UUID diaryId) {
        diaryEntryRepository.findById(diaryId).ifPresentOrElse(diaryEntryRepository::delete,
                () -> {
                    throw new ResourceNotFoundException(DIARY_ENTRY_NOT_FOUND.getMessage());
                });
    }

    private void validate(AddDiaryEntry addDiaryEntry, Optional<FoodEntity> optionalFoodEntity) {
        if(optionalFoodEntity.isEmpty()) {
            throw new ResourceNotFoundException(FOOD_NOT_FOUND.getMessage());
        }
        if(userRepository.findById(addDiaryEntry.getUserId()).isEmpty()) {
            throw new ResourceNotFoundException(USER_NOT_FOUND.getMessage());
        }
        if(unitRepository.findById(addDiaryEntry.getUnitId()).isEmpty()) {
            throw new ResourceNotFoundException(UNIT_NOT_FOUND.getMessage());
        }
    }

}
