package com.mj.calorietracker.dto.add;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddLocalDiaryEntry extends AddDiaryEntry {
    @NotNull
    private Integer localId;
}
