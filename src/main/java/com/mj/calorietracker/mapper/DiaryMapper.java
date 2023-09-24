package com.mj.calorietracker.mapper;

import com.mj.calorietracker.model.DiaryEntry;
import com.mj.calorietracker.model.add.AddDiaryEntry;
import com.mj.calorietracker.model.add.AddDiaryEntryWithFood;
import com.mj.calorietracker.repository.dao.DiaryEntryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(uses = FoodMapper.class)
public interface DiaryMapper {
    DiaryMapper diaryMapper = Mappers.getMapper(DiaryMapper.class);

    @Mapping(target="unitId", source = "unit.id")
    DiaryEntry toModel(DiaryEntryEntity entity);

    @Mapping(target="unit.id", source = "model.unitId")
    @Mapping(target="food.id", source = "foodId")
    DiaryEntryEntity toEntity(AddDiaryEntryWithFood model, UUID foodId);

    @Mapping(target="unit.id", source = "unitId")
    @Mapping(target="food.id", source = "foodId")
    DiaryEntryEntity toEntity(AddDiaryEntry addDiaryEntry);
}
