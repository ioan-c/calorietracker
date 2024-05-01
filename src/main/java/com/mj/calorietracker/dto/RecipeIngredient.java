package com.mj.calorietracker.dto;


public record RecipeIngredient(
        Integer id,
        Integer recipeId,
        Food food,
        FoodUnit unit,
        Double quantity) {
}
