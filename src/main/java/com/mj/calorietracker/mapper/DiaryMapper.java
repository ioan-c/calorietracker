package com.mj.calorietracker.mapper;

import com.mj.calorietracker.model.NutritionInfo;
import com.mj.calorietracker.model.add.AddDiaryEntryAndFood;
import com.mj.calorietracker.model.add.AddDiaryEntry;
import com.mj.calorietracker.model.update.UpdateDiaryEntry;
import com.mj.calorietracker.repository.dao.DiaryEntryEntity;
import com.mj.calorietracker.repository.dao.UnitEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiaryMapper {

    private final ModelMapper modelMapper;

    public UpdateDiaryEntry toModel(DiaryEntryEntity diaryEntryEntity) {
        return modelMapper.map(diaryEntryEntity, UpdateDiaryEntry.class);
    }
    public DiaryEntryEntity toEntity(AddDiaryEntryAndFood addDiaryEntryAndFood) {
        return new DiaryEntryEntity().setName(addDiaryEntryAndFood.getName())
                .setBrand(addDiaryEntryAndFood.getBrand())
                .setEntryDate(addDiaryEntryAndFood.getEntryDate())
                .setUserId(addDiaryEntryAndFood.getUserId())
                .setUnit(new UnitEntity().setId(addDiaryEntryAndFood.getUnitId()))
                .setServingQuantity(addDiaryEntryAndFood.getServingQuantity())
                .setMeal(addDiaryEntryAndFood.getMeal())
                .setCalories(addDiaryEntryAndFood.getNutritionInfo().getCalories())
                .setFat(addDiaryEntryAndFood.getNutritionInfo().getFat())
                .setFatSaturated(addDiaryEntryAndFood.getNutritionInfo().getFatSaturated())
                .setFatTrans(addDiaryEntryAndFood.getNutritionInfo().getFatTrans())
                .setFatPolyunsaturated(addDiaryEntryAndFood.getNutritionInfo().getFatPolyunsaturated())
                .setFatMonounsaturated(addDiaryEntryAndFood.getNutritionInfo().getFatMonounsaturated())
                .setCholesterol(addDiaryEntryAndFood.getNutritionInfo().getCholesterol())
                .setCarbohydrates(addDiaryEntryAndFood.getNutritionInfo().getCarbohydrates())
                .setFiber(addDiaryEntryAndFood.getNutritionInfo().getFiber())
                .setSugar(addDiaryEntryAndFood.getNutritionInfo().getSugar())
                .setProtein(addDiaryEntryAndFood.getNutritionInfo().getProtein())
                .setSodium(addDiaryEntryAndFood.getNutritionInfo().getSodium())
                .setPotassium(addDiaryEntryAndFood.getNutritionInfo().getPotassium())
                .setCalcium(addDiaryEntryAndFood.getNutritionInfo().getCalcium())
                .setIron(addDiaryEntryAndFood.getNutritionInfo().getIron())
                .setVitaminA(addDiaryEntryAndFood.getNutritionInfo().getVitaminA())
                .setVitaminC(addDiaryEntryAndFood.getNutritionInfo().getVitaminC())
                .setVitaminD(addDiaryEntryAndFood.getNutritionInfo().getVitaminD());
    }

    public DiaryEntryEntity toEntity(AddDiaryEntry addDiaryEntry, String brand, String name, NutritionInfo nutritionInfo) {
        return new DiaryEntryEntity().setName(brand)
                .setBrand(name)
                .setEntryDate(addDiaryEntry.getEntryDate())
                .setUserId(addDiaryEntry.getUserId())
                .setUnit(new UnitEntity().setId(addDiaryEntry.getUnitId()))
                .setServingQuantity(addDiaryEntry.getServingQuantity())
                .setMeal(addDiaryEntry.getMeal())
                .setCalories(nutritionInfo.getCalories())
                .setFat(nutritionInfo.getFat())
                .setFatSaturated(nutritionInfo.getFatSaturated())
                .setFatTrans(nutritionInfo.getFatTrans())
                .setFatPolyunsaturated(nutritionInfo.getFatPolyunsaturated())
                .setFatMonounsaturated(nutritionInfo.getFatMonounsaturated())
                .setCholesterol(nutritionInfo.getCholesterol())
                .setCarbohydrates(nutritionInfo.getCarbohydrates())
                .setFiber(nutritionInfo.getFiber())
                .setSugar(nutritionInfo.getSugar())
                .setProtein(nutritionInfo.getProtein())
                .setSodium(nutritionInfo.getSodium())
                .setPotassium(nutritionInfo.getPotassium())
                .setCalcium(nutritionInfo.getCalcium())
                .setIron(nutritionInfo.getIron())
                .setVitaminA(nutritionInfo.getVitaminA())
                .setVitaminC(nutritionInfo.getVitaminC())
                .setVitaminD(nutritionInfo.getVitaminD());
    }
    public DiaryEntryEntity toEntity(UpdateDiaryEntry updateDiaryEntry) {
        return modelMapper.map(updateDiaryEntry, DiaryEntryEntity.class);
    }
}
