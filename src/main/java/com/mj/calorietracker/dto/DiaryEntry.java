package com.mj.calorietracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DiaryEntry {
    private Integer id;
    private LocalDate entryDate;
    private FoodUnit unit;
    private Double servingQuantity;
    private Food food;
}
