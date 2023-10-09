package com.mj.calorietracker.mapper;

import com.mj.calorietracker.dto.DiaryEntry;
import com.mj.calorietracker.dto.add.AddDiaryEntry;
import com.mj.calorietracker.repository.dao.DiaryEntryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {FoodMapper.class, UnitMapper.class})
public interface DiaryMapper {
    DiaryMapper diaryMapper = Mappers.getMapper(DiaryMapper.class);

    DiaryEntry toDto(DiaryEntryEntity entity);

    @Mapping(target="food.id", source = "foodId")
    @Mapping(target="unit.id", source = "foodUnitId")
    DiaryEntryEntity toEntity(AddDiaryEntry addDiaryEntry);
}
