package com.mj.calorietracker.dto;


public record Recipe(
        Integer id,
        String name,
        String description,
        Integer cookedWeight) {
}
