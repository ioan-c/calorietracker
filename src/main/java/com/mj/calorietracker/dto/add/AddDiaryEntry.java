package com.mj.calorietracker.dto.add;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddDiaryEntry extends AbstractAddDiaryEntry {
    @NotNull
    private UUID foodId;
}
