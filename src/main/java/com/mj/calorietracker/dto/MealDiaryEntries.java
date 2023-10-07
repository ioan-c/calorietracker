package com.mj.calorietracker.dto;

import com.mj.calorietracker.enums.Meal;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MealDiaryEntries {

    private Meal meal;
    private List<DiaryEntry> diaryEntries;
}
