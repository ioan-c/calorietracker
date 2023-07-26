package com.mj.calorietracker.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Food {
    private UUID id;
    private String barcode;
    private String name;
    private String brand;
    private NutritionInfo nutritionInfo;
}
