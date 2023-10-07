package com.mj.calorietracker.dto.add;

import com.mj.calorietracker.enums.Meal;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class AbstractAddDiaryEntry {
    @NotNull
    private LocalDate entryDate;
    @NotNull
    private UUID userId;
    @NotNull
    private UUID unitId;
    @NotNull
    @PositiveOrZero()
    @Digits(integer = 3, fraction = 2)
    private Double servingQuantity;
    @NotNull
    private Meal meal;
}
