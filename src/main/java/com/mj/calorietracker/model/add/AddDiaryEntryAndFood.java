package com.mj.calorietracker.model.add;

import com.mj.calorietracker.enums.Meal;
import com.mj.calorietracker.model.NutritionInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class AddDiaryEntryAndFood {

    @Pattern(regexp = "^[0-9]{13}$", message = "must have a numeric value of 13 digits")
    private String barcode;
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;
    @Size(max = 100)
    private String brand;
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
    private NutritionInfo nutritionInfo;
}
