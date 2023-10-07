package com.mj.calorietracker.dto.update;

import com.mj.calorietracker.dto.add.AddFood;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class UpdateFood extends AddFood {
    @NotNull
    private UUID id;
}
