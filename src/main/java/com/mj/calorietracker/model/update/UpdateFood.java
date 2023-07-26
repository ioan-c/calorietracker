package com.mj.calorietracker.model.update;

import com.mj.calorietracker.model.add.AddFood;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class UpdateFood extends AddFood {
    @NotNull
    private UUID id;
}
