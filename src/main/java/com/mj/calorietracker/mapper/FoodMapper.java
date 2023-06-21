package com.mj.calorietracker.mapper;

import com.mj.calorietracker.model.Food;
import com.mj.calorietracker.model.NutritionInfo;
import com.mj.calorietracker.model.add.AddFood;
import com.mj.calorietracker.model.add.AddDiaryEntryAndFood;
import com.mj.calorietracker.model.update.UpdateFood;
import com.mj.calorietracker.repository.dao.FoodEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class FoodMapper {

    private final ModelMapper modelMapper;

    public Food toModel(FoodEntity foodEntity) {
        return modelMapper.map(foodEntity, Food.class);
    }
    public AddFood toModel(AddDiaryEntryAndFood addDiaryEntryAndFood) {
        return modelMapper.map(addDiaryEntryAndFood, AddFood.class);
    }
    public FoodEntity toEntity(AddFood addFood) {
        return modelMapper.map(addFood, FoodEntity.class).setCreatedDate(LocalDate.now());
    }
    public FoodEntity toEntity(UpdateFood updateFood) {
        return modelMapper.map(updateFood, FoodEntity.class).setCreatedDate(LocalDate.now());
    }
    public NutritionInfo getNutritionalInfo(FoodEntity foodEntity) {
        return modelMapper.map(foodEntity, NutritionInfo.class);
    }
}
