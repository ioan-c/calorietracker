package com.mj.calorietracker.dto.update;

import com.mj.calorietracker.dto.add.AddFood;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFood extends AddFood {
    @NotNull
    private Integer id;
}
