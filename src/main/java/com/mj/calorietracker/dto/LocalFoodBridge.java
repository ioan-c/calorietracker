package com.mj.calorietracker.dto;

import java.util.UUID;

public record LocalFoodBridge (
        UUID foodId,
        Integer localFoodId
) {}
