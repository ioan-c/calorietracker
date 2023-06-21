package com.mj.calorietracker.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class DiaryEntry {

    private UUID id;
    private LocalDate entryDate;
    private UUID unitId;
    private Double servingQuantity;
    private Integer calories;
    private Double fat;
    private Double fatSaturated;
    private Double fatTrans;
    private Double fatPolyunsaturated;
    private Double fatMonounsaturated;
    private Integer cholesterol;
    private Double carbohydrates;
    private Double fiber;
    private Double sugar;
    private Double protein;
    private Integer sodium;
    private Integer potassium;
    private Integer calcium;
    private Integer iron;
    private Integer vitaminA;
    private Integer vitaminC;
    private Integer vitaminD;
}
