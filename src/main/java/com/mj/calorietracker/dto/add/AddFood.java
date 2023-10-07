package com.mj.calorietracker.dto.add;

import com.mj.calorietracker.dto.NutritionInfo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.Objects;

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
    @Valid
    @NotNull
    private NutritionInfo nutritionInfo;

    public static Comparator<AddFood> comparator() {
        return (f1, f2) -> {
            if (f1 == f2)
                return 0;

            if (Objects.nonNull(f1) && Objects.nonNull(f2)) {
                if (Objects.nonNull(f1.getBarcode()) && Objects.equals(f1.getBarcode(), f2.getBarcode())) {
                    return 0;
                } else if (Objects.equals(f1.getName(), f2.getName()) && Objects.equals(f1.getBrand(), f2.getBrand())) {
                    return 0;
                }
            }

            return -1;
        };
    }
}
