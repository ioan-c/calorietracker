package com.mj.calorietracker.model.add;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddLocalFood extends AddFood {
    @NotNull
    private Integer localId;
}
