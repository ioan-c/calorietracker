package com.mj.calorietracker.model.add;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.mj.calorietracker.model.NutritionInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddFood {
    @Pattern(regexp = "^[0-9]{13}$", message = "must have a numeric value of 13 digits")
    private String barcode;
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;
    @Size(max = 100)
    private String brand;
    @JsonUnwrapped
    @Valid
    private NutritionInfo nutritionInfo;
}
