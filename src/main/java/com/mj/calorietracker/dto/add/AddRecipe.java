package com.mj.calorietracker.dto.add;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record AddRecipe(
        @NotBlank
        @Size(min = 1, max = 100)
        String name,
        @Size(min = 1, max = 5000)
        String description,
        @NotEmpty
        List<@Valid AddRecipeIngredient> ingredients,
        @NotNull
        @Positive
        Integer cookedWeight
) {
}
