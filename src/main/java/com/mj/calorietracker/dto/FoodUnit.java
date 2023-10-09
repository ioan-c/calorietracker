package com.mj.calorietracker.dto;

public record FoodUnit(
        Integer id,
        String name,
        String abbrev,
        Double weightInGrams
) {
}
