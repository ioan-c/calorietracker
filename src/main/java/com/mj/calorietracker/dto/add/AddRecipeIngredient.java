package com.mj.calorietracker.dto.add;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddRecipeIngredient(
        @NotNull
        Integer foodId,
        @NotNull
        Integer foodUnitId,
        @NotNull
        @Positive
        Double quantity
) {
}
