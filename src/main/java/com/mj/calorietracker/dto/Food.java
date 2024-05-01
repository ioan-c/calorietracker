package com.mj.calorietracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Food {
    private Integer id;
    private String barcode;
    private String name;
    private String brand;
    private NutritionInfo nutritionInfo;
}
