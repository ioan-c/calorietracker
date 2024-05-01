package com.mj.calorietracker.dto.update;

import jakarta.validation.constraints.Size;

public record UpdateRecipeDescription(
        @Size(min = 1, max = 5000)
        String description) {
}
