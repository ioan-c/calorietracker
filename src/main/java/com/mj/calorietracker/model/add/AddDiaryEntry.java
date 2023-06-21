package com.mj.calorietracker.model.add;

import com.mj.calorietracker.enums.Meal;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class AddDiaryEntry {

    private LocalDate entryDate;
    private UUID userId;
    private UUID unitId;
    private UUID foodId;
    @NotNull
    @PositiveOrZero()
    @Digits(integer = 3, fraction = 2)
    private Double servingQuantity;
    private Meal meal;
}
