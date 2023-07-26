package com.mj.calorietracker.model.add;

import com.mj.calorietracker.enums.Meal;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class AddDiaryEntryAndFood {
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
    @Valid
    private AddFood food;
}
