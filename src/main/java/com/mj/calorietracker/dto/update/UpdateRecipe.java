package com.mj.calorietracker.dto.update;

import com.mj.calorietracker.dto.add.AddRecipeIngredient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateRecipe(
        @NotNull
        Integer id,
        @Size(min = 1, max = 100)
        String name,
        @Size(min = 1, max = 5000)
        String description,
        List<@Valid AddRecipeIngredient> addIngredients,
        List<@Valid UpdateRecipeIngredient> updateIngredients,
        List<Integer> deleteIngredients,
        @Positive
        Integer cookedWeight) {
}
