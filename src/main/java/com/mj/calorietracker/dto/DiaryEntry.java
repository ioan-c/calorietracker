package com.mj.calorietracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class DiaryEntry {
    private UUID id;
    private LocalDate entryDate;
    private FoodUnit unit;
    private Double servingQuantity;
    private Food food;
}
