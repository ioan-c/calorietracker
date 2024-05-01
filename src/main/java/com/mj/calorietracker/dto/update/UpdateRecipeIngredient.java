package com.mj.calorietracker.dto.update;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdateRecipeIngredient(
        @NotNull
        Integer id,
        Integer foodUnitId,
        @NotNull
        @Positive
        Double quantity) {
}
