package com.mj.calorietracker.dto;

import java.util.UUID;

public record LocalResourceBridge(
        UUID resourceId,
        Integer localResourceId
) {
}
