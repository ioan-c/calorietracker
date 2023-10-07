package com.mj.calorietracker.dto.add;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddDiaryEntryWithFood extends AbstractAddDiaryEntry {
    @Valid
    @NotNull
    private AddFood food;
}
