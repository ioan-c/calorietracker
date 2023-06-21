package com.mj.calorietracker.exception;

import com.mj.calorietracker.model.Food;
import lombok.Getter;

@Getter
public class FoodAlreadyExistsException extends RuntimeException {

    private final Food existingFood;

    public FoodAlreadyExistsException(Food existingFood, String message) {
        super(message);
        this.existingFood = existingFood;
    }
}
