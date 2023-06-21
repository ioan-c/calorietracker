package com.mj.calorietracker.util;

import com.mj.calorietracker.model.NutritionInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NutritionValueConvertor {

    public static NutritionInfo convertToServingQuantity(NutritionInfo nutritionInfo, Double servingQuantity) {
        NutritionInfo result = new NutritionInfo();
        result.setCalories(convert(nutritionInfo.getCalories(), servingQuantity));
        result.setFat(convert(nutritionInfo.getFat(), servingQuantity));
        result.setFatSaturated(convert(nutritionInfo.getFatSaturated(), servingQuantity));
        result.setFatTrans(convert(nutritionInfo.getFatTrans(), servingQuantity));
        result.setFatPolyunsaturated(convert(nutritionInfo.getFatPolyunsaturated(), servingQuantity));
        result.setFatMonounsaturated(convert(nutritionInfo.getFatMonounsaturated(), servingQuantity));
        result.setCholesterol(convert(nutritionInfo.getCholesterol(), servingQuantity));
        result.setCarbohydrates(convert(nutritionInfo.getCarbohydrates(), servingQuantity));
        result.setFiber(convert(nutritionInfo.getFiber(), servingQuantity));
        result.setSugar(convert(nutritionInfo.getSugar(), servingQuantity));
        result.setProtein(convert(nutritionInfo.getProtein(), servingQuantity));
        result.setSodium(convert(nutritionInfo.getSodium(), servingQuantity));
        result.setPotassium(convert(nutritionInfo.getPotassium(), servingQuantity));
        result.setCalcium(convert(nutritionInfo.getCalcium(), servingQuantity));
        result.setIron(convert(nutritionInfo.getIron(), servingQuantity));
        result.setVitaminA(convert(nutritionInfo.getVitaminA(), servingQuantity));
        result.setVitaminC(convert(nutritionInfo.getVitaminC(), servingQuantity));
        result.setVitaminD(convert(nutritionInfo.getVitaminD(), servingQuantity));

        return result;
    }

    private static int convert(Integer value, Double servingQuantity) {
        return roundInt(servingQuantity * value / 100);
    }

    private static double convert(Double value, Double servingQuantity) {
        return roundDouble(servingQuantity * value / 100);
    }

    private static double roundDouble(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private static int roundInt(double value) {
        return (int) Math.round(value);
    }

}
