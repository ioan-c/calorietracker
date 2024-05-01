package com.mj.calorietracker.util;

import com.mj.calorietracker.dto.Food;
import com.mj.calorietracker.dto.FoodUnit;
import com.mj.calorietracker.dto.NutritionInfo;
import com.mj.calorietracker.dto.RecipeIngredient;
import com.mj.calorietracker.repository.dao.FoodEntity;
import com.mj.calorietracker.repository.dao.RecipeIngredientEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import static com.mj.calorietracker.mapper.RecipeMapper.recipeMapper;
import static com.mj.calorietracker.util.Constants.GRAM_UNIT_ID;

public class NutritionValueConvertor {

    public static FoodEntity getRecipeFood(String name, List<RecipeIngredientEntity> recipeIngredientEntities, Integer cookedWeight){
        List<RecipeIngredient> recipeIngredients = recipeIngredientEntities.stream().map(recipeMapper::toRecipeIngredient).toList();
        List<IngredientInGrams> ingredientsInGrams = getIngredientsInGrams(recipeIngredients);
        List<NutritionInfoForQuantity> nutritionInfosByQuantity = getNutritionInfosByQuantity(ingredientsInGrams);
        NutritionInfoForQuantity totalNutrition = getTotalNutrition(nutritionInfosByQuantity);

        return new FoodEntity()
                .setName(name)
                .setCalories(getIntNutrientPer100g(totalNutrition.calories(), cookedWeight))
                .setFat(getDoubleNutrientPer100g(totalNutrition.fat(), cookedWeight))
                .setFatSaturated(getDoubleNutrientPer100g(totalNutrition.fatSaturated(), cookedWeight))
                .setFatTrans(getDoubleNutrientPer100g(totalNutrition.fatTrans(), cookedWeight))
                .setFatPolyunsaturated(getDoubleNutrientPer100g(totalNutrition.fatPolyunsaturated(), cookedWeight))
                .setFatMonounsaturated(getDoubleNutrientPer100g(totalNutrition.fatMonounsaturated(), cookedWeight))
                .setCholesterol(getIntNutrientPer100g(totalNutrition.cholesterol(), cookedWeight))
                .setCarbohydrates(getDoubleNutrientPer100g(totalNutrition.carbohydrates(), cookedWeight))
                .setFiber(getDoubleNutrientPer100g(totalNutrition.fiber(), cookedWeight))
                .setSugar(getDoubleNutrientPer100g(totalNutrition.sugar(), cookedWeight))
                .setProtein(getDoubleNutrientPer100g(totalNutrition.protein(), cookedWeight))
                .setSodium(getIntNutrientPer100g(totalNutrition.sodium(), cookedWeight))
                .setPotassium(getIntNutrientPer100g(totalNutrition.potassium(), cookedWeight))
                .setCalcium(getIntNutrientPer100g(totalNutrition.calcium(), cookedWeight))
                .setIron(getIntNutrientPer100g(totalNutrition.iron(), cookedWeight))
                .setVitaminA(getIntNutrientPer100g(totalNutrition.vitaminA(), cookedWeight))
                .setVitaminC(getIntNutrientPer100g(totalNutrition.vitaminC(), cookedWeight))
                .setVitaminD(getIntNutrientPer100g(totalNutrition.vitaminD(), cookedWeight));
    }

    private static List<IngredientInGrams> getIngredientsInGrams(List<RecipeIngredient> recipeIngredients) {
        return recipeIngredients.stream().map(NutritionValueConvertor::getIngredientInGrams).toList();
    }

    private static NutritionInfoForQuantity getTotalNutrition(List<NutritionInfoForQuantity> nutritionInfos) {
        return nutritionInfos.stream()
                .reduce(NutritionInfoForQuantity::add)
                .map(NutritionInfoForQuantity::getCleanNutrition)
                .orElseThrow(() -> new RuntimeException("An error occurred during food nutrition calculus."));
    }

    private static IngredientInGrams getIngredientInGrams(RecipeIngredient ingredient) {
        if (!ingredient.unit().id().equals(GRAM_UNIT_ID)) {
            return convertToIngredientInGrams(ingredient);
        } else {
            return new IngredientInGrams(ingredient.food(), ingredient.quantity());
        }
    }

    private static IngredientInGrams convertToIngredientInGrams(RecipeIngredient ingredient) {
        FoodUnit unit = ingredient.unit();
        Double quantityInGrams = ingredient.quantity() * unit.weightInGrams();
        return new IngredientInGrams(ingredient.food(), quantityInGrams);
    }

    private static int roundInt(double value) {
        return (int) Math.round(value);
    }

    private static double roundDouble(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private static Double getNutrientQuantity(Integer nutrientPer100g, Double quantity) {
        return Optional.ofNullable(nutrientPer100g)
                .map(nutrient -> nutrient * quantity / 100)
                .orElse(null);
    }
    private static Double getNutrientQuantity(Double nutrientPer100g, Double quantity) {
        return Optional.ofNullable(nutrientPer100g)
                .map(nutrient -> nutrient * quantity / 100)
                .orElse(null);
    }

    private static Integer getIntNutrientPer100g(Double totalNutrient, Integer cookedWeight) {
        return Optional.ofNullable(totalNutrient)
                .map(nutrient -> roundInt(nutrient * 100 / cookedWeight))
                .orElse(null);
    }

    private static Double getDoubleNutrientPer100g(Double totalNutrient, Integer cookedWeight) {
        return Optional.ofNullable(totalNutrient)
                .map(nutrient -> roundDouble(nutrient * 100 / cookedWeight))
                .orElse(null);
    }

    private static List<NutritionInfoForQuantity> getNutritionInfosByQuantity(List<IngredientInGrams> ingredientsInGrams) {
        return ingredientsInGrams.stream()
                .map(ingredient -> {
                    NutritionInfo nutritionInfoPer100g = ingredient.food().getNutritionInfo();
                    Double ingredientQuantity = ingredient.quantity();
                    var calories = getNutrientQuantity(nutritionInfoPer100g.getCalories(), ingredientQuantity);
                    var fat = getNutrientQuantity(nutritionInfoPer100g.getFat(), ingredientQuantity);
                    var fatSaturated = getNutrientQuantity(nutritionInfoPer100g.getFatSaturated(), ingredientQuantity);
                    var fatTrans = getNutrientQuantity(nutritionInfoPer100g.getFatTrans(), ingredientQuantity);
                    var fatPolyunsaturated = getNutrientQuantity(nutritionInfoPer100g.getFatPolyunsaturated(), ingredientQuantity);
                    var fatMonounsaturated = getNutrientQuantity(nutritionInfoPer100g.getFatMonounsaturated(), ingredientQuantity);
                    var cholesterol = getNutrientQuantity(nutritionInfoPer100g.getCholesterol(), ingredientQuantity);
                    var carbohydrates = getNutrientQuantity(nutritionInfoPer100g.getCarbohydrates(), ingredientQuantity);
                    var fiber = getNutrientQuantity(nutritionInfoPer100g.getFiber(), ingredientQuantity);
                    var sugar = getNutrientQuantity(nutritionInfoPer100g.getSugar(), ingredientQuantity);
                    var protein = getNutrientQuantity(nutritionInfoPer100g.getProtein(), ingredientQuantity);
                    var sodium = getNutrientQuantity(nutritionInfoPer100g.getSodium(), ingredientQuantity);
                    var potassium = getNutrientQuantity(nutritionInfoPer100g.getPotassium(), ingredientQuantity);
                    var calcium = getNutrientQuantity(nutritionInfoPer100g.getCalcium(), ingredientQuantity);
                    var iron = getNutrientQuantity(nutritionInfoPer100g.getIron(), ingredientQuantity);
                    var vitaminA = getNutrientQuantity(nutritionInfoPer100g.getVitaminA(), ingredientQuantity);
                    var vitaminC = getNutrientQuantity(nutritionInfoPer100g.getVitaminC(), ingredientQuantity);
                    var vitaminD = getNutrientQuantity(nutritionInfoPer100g.getVitaminD(), ingredientQuantity);
                    return new NutritionInfoForQuantity(
                            calories,
                            fat,
                            fatSaturated,
                            fatTrans,
                            fatPolyunsaturated,
                            fatMonounsaturated,
                            cholesterol,
                            carbohydrates,
                            fiber,
                            sugar,
                            protein,
                            sodium,
                            potassium,
                            calcium,
                            iron,
                            vitaminA,
                            vitaminC,
                            vitaminD
                    );
                }).toList();
    }

    private record IngredientInGrams(
         Food food,
         Double quantity
    ){}

    private record NutritionInfoForQuantity(
        Double calories,
        Double fat,
        Double fatSaturated,
        Double fatTrans,
        Double fatPolyunsaturated,
        Double fatMonounsaturated,
        Double cholesterol,
        Double carbohydrates,
        Double fiber,
        Double sugar,
        Double protein,
        Double sodium,
        Double potassium,
        Double calcium,
        Double iron,
        Double vitaminA,
        Double vitaminC,
        Double vitaminD
    ){
        private NutritionInfoForQuantity add(NutritionInfoForQuantity other) {
            return new NutritionInfoForQuantity(
                    addNutrients(this.calories, other.calories),
                    addNutrients(this.fat, other.fat),
                    addNutrients(this.fatSaturated, other.fatSaturated),
                    addNutrients(this.fatTrans, other.fatTrans),
                    addNutrients(this.fatPolyunsaturated, other.fatPolyunsaturated),
                    addNutrients(this.fatMonounsaturated, other.fatMonounsaturated),
                    addNutrients(this.cholesterol, other.cholesterol),
                    addNutrients(this.carbohydrates, other.carbohydrates),
                    addNutrients(this.fiber, other.fiber),
                    addNutrients(this.sugar, other.sugar),
                    addNutrients(this.protein, other.protein),
                    addNutrients(this.sodium, other.sodium),
                    addNutrients(this.potassium, other.potassium),
                    addNutrients(this.calcium, other.calcium),
                    addNutrients(this.iron, other.iron),
                    addNutrients(this.vitaminA, other.vitaminA),
                    addNutrients(this.vitaminC, other.vitaminC),
                    addNutrients(this.vitaminD, other.vitaminD)
            );
        }

        private double addNutrients(Double nutrient1, Double nutrient2) {
            return Optional.ofNullable(nutrient1).orElse(0D) + Optional.ofNullable(nutrient2).orElse(0D);
        }

        private NutritionInfoForQuantity getCleanNutrition() {
            Double calories = this.calories;
            Double fat = this.fat;
            Double fatSaturated = convert0ToNull(this.fatSaturated);
            Double fatTrans = convert0ToNull(this.fatTrans);
            Double fatPolyunsaturated = convert0ToNull(this.fatPolyunsaturated);
            Double fatMonounsaturated = convert0ToNull(this.fatMonounsaturated);
            Double cholesterol = convert0ToNull(this.cholesterol);
            Double carbohydrates = this.carbohydrates;
            Double fiber = convert0ToNull(this.fiber);
            Double sugar = convert0ToNull(this.sugar);
            Double protein = this.protein;
            Double sodium = convert0ToNull(this.sodium);
            Double potassium = convert0ToNull(this.potassium);
            Double calcium = convert0ToNull(this.calcium);
            Double iron = convert0ToNull(this.iron);
            Double vitaminA = convert0ToNull(this.vitaminA);
            Double vitaminC = convert0ToNull(this.vitaminC);
            Double vitaminD = convert0ToNull(this.vitaminD);
            return new NutritionInfoForQuantity(
                    calories,
                    fat,
                    fatSaturated,
                    fatTrans,
                    fatPolyunsaturated,
                    fatMonounsaturated,
                    cholesterol,
                    carbohydrates,
                    fiber,
                    sugar,
                    protein,
                    sodium,
                    potassium,
                    calcium,
                    iron,
                    vitaminA,
                    vitaminC,
                    vitaminD
            );
        }

        private Double convert0ToNull(Double nutrient) {
            return nutrient == 0D ? null : nutrient;
        }
    }
}
