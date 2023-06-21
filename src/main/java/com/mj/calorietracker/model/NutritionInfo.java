package com.mj.calorietracker.model;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NutritionInfo {
    @NotNull
    @PositiveOrZero()
    @Max(value = 900)
    private Integer calories;
    @NotNull
    @PositiveOrZero()
    @Digits(integer = 3, fraction = 2)
    private Double fat;
    @PositiveOrZero()
    @Digits(integer = 3, fraction = 2)
    private Double fatSaturated;
    @PositiveOrZero()
    @Digits(integer = 3, fraction = 2)
    private Double fatTrans;
    @PositiveOrZero()
    @Digits(integer = 3, fraction = 2)
    private Double fatPolyunsaturated;
    @PositiveOrZero()
    @Digits(integer = 3, fraction = 2)
    private Double fatMonounsaturated;
    @PositiveOrZero()
    @Max(value = 10000)
    private Integer cholesterol;
    @NotNull
    @PositiveOrZero()
    @Digits(integer = 3, fraction = 2)
    private Double carbohydrates;
    @PositiveOrZero()
    @Digits(integer = 3, fraction = 2)
    private Double fiber;
    @PositiveOrZero()
    @Digits(integer = 3, fraction = 2)
    private Double sugar;
    @NotNull
    @PositiveOrZero()
    @Digits(integer = 3, fraction = 2)
    private Double protein;
    @PositiveOrZero()
    @Max(value = 36000)
    private Integer sodium;
    @PositiveOrZero()
    @Max(value = 36000)
    private Integer potassium;
    @PositiveOrZero()
    @Max(value = 36000)
    private Integer calcium;
    @PositiveOrZero()
    @Max(value = 36000)
    private Integer iron;
    @PositiveOrZero()
    @Max(value = 36000)
    private Integer vitaminA;
    @PositiveOrZero()
    @Max(value = 36000)
    private Integer vitaminC;
    @PositiveOrZero()
    @Max(value = 36000)
    private Integer vitaminD;
}
