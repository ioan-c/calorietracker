package com.mj.calorietracker.model;

import java.util.UUID;

public record LocalFoodBridge (
        UUID foodId,
        Integer localFoodId
) {}
