package com.mj.calorietracker.model.add;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddDiaryEntry extends AbstractAddDiaryEntry {
    @NotNull
    private UUID foodId;
}
