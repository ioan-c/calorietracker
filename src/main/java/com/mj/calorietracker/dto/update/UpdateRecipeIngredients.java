package com.mj.calorietracker.dto.update;

import com.mj.calorietracker.dto.add.AddRecipeIngredient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record UpdateRecipeIngredients(
        @Positive
        Integer cookedWeight,
        List<@Valid AddRecipeIngredient> addIngredients,
        List<@Valid UpdateRecipeIngredient> updateIngredients,
        List<Integer> deleteIngredients) {
}
