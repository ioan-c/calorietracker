package com.mj.calorietracker.mapper;

import com.mj.calorietracker.dto.FoodUnit;
import com.mj.calorietracker.repository.dao.FoodUnitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UnitMapper {
    UnitMapper unitMapper = Mappers.getMapper(UnitMapper.class);

    @Mapping(target = "name", source = "unit.name")
    @Mapping(target = "abbrev", source = "unit.abbrev")
    FoodUnit toDTO(FoodUnitEntity foodEntity);
}
