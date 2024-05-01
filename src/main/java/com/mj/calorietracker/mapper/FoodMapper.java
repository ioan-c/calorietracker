package com.mj.calorietracker.mapper;

import com.mj.calorietracker.dto.Food;
import com.mj.calorietracker.dto.add.AddFood;
import com.mj.calorietracker.repository.dao.FoodEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FoodMapper {
    FoodMapper foodMapper = Mappers.getMapper(FoodMapper.class);
    @Mapping(target="nutritionInfo.calories", source = "calories")
    @Mapping(target="nutritionInfo.fat", source = "fat")
    @Mapping(target="nutritionInfo.fatSaturated", source = "fatSaturated")
    @Mapping(target="nutritionInfo.fatTrans", source = "fatTrans")
    @Mapping(target="nutritionInfo.fatPolyunsaturated", source = "fatPolyunsaturated")
    @Mapping(target="nutritionInfo.fatMonounsaturated", source = "fatMonounsaturated")
    @Mapping(target="nutritionInfo.cholesterol", source = "cholesterol")
    @Mapping(target="nutritionInfo.carbohydrates", source = "carbohydrates")
    @Mapping(target="nutritionInfo.fiber", source = "fiber")
    @Mapping(target="nutritionInfo.sugar", source = "sugar")
    @Mapping(target="nutritionInfo.protein", source = "protein")
    @Mapping(target="nutritionInfo.sodium", source = "sodium")
    @Mapping(target="nutritionInfo.potassium", source = "potassium")
    @Mapping(target="nutritionInfo.calcium", source = "calcium")
    @Mapping(target="nutritionInfo.iron", source = "iron")
    @Mapping(target="nutritionInfo.vitaminA", source = "vitaminA")
    @Mapping(target="nutritionInfo.vitaminC", source = "vitaminC")
    @Mapping(target="nutritionInfo.vitaminD", source = "vitaminD")
    Food toFood(FoodEntity foodEntity);

    @Mapping(target="calories", source = "nutritionInfo.calories")
    @Mapping(target="fat", source = "nutritionInfo.fat")
    @Mapping(target="fatSaturated", source = "nutritionInfo.fatSaturated")
    @Mapping(target="fatTrans", source = "nutritionInfo.fatTrans")
    @Mapping(target="fatPolyunsaturated", source = "nutritionInfo.fatPolyunsaturated")
    @Mapping(target="fatMonounsaturated", source = "nutritionInfo.fatMonounsaturated")
    @Mapping(target="cholesterol", source = "nutritionInfo.cholesterol")
    @Mapping(target="carbohydrates", source = "nutritionInfo.carbohydrates")
    @Mapping(target="fiber", source = "nutritionInfo.fiber")
    @Mapping(target="sugar", source = "nutritionInfo.sugar")
    @Mapping(target="protein", source = "nutritionInfo.protein")
    @Mapping(target="sodium", source = "nutritionInfo.sodium")
    @Mapping(target="potassium", source = "nutritionInfo.potassium")
    @Mapping(target="calcium", source = "nutritionInfo.calcium")
    @Mapping(target="iron", source = "nutritionInfo.iron")
    @Mapping(target="vitaminA", source = "nutritionInfo.vitaminA")
    @Mapping(target="vitaminC", source = "nutritionInfo.vitaminC")
    @Mapping(target="vitaminD", source = "nutritionInfo.vitaminD")
    FoodEntity toEntity(AddFood addFood);
}
