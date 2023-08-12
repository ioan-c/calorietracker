package com.mj.calorietracker.mapper;

import com.mj.calorietracker.model.add.AddDiaryEntryWithFood;
import com.mj.calorietracker.model.add.AddDiaryEntry;
import com.mj.calorietracker.model.DiaryEntry;
import com.mj.calorietracker.repository.dao.DiaryEntryEntity;
import com.mj.calorietracker.repository.dao.FoodEntity;
import com.mj.calorietracker.repository.dao.UnitEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DiaryMapper {

    private final ModelMapper modelMapper;

    public DiaryEntry toModel(DiaryEntryEntity diaryEntryEntity) {
        return modelMapper.map(diaryEntryEntity, DiaryEntry.class);
    }
    public DiaryEntryEntity toEntity(AddDiaryEntryWithFood addDiaryEntryWithFood, UUID foodId) {
        return new DiaryEntryEntity()
                .setEntryDate(addDiaryEntryWithFood.getEntryDate())
                .setUserId(addDiaryEntryWithFood.getUserId())
                .setUnit(new UnitEntity().setId(addDiaryEntryWithFood.getUnitId()))
                .setFood(new FoodEntity().setId(foodId))
                .setServingQuantity(addDiaryEntryWithFood.getServingQuantity())
                .setMeal(addDiaryEntryWithFood.getMeal());
    }

    public DiaryEntryEntity toEntity(AddDiaryEntry addDiaryEntry) {
        return new DiaryEntryEntity()
                .setEntryDate(addDiaryEntry.getEntryDate())
                .setUserId(addDiaryEntry.getUserId())
                .setUnit(new UnitEntity().setId(addDiaryEntry.getUnitId()))
                .setFood(new FoodEntity().setId(addDiaryEntry.getFoodId()))
                .setServingQuantity(addDiaryEntry.getServingQuantity())
                .setMeal(addDiaryEntry.getMeal());
    }
}
