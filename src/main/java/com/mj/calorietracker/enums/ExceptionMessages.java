package com.mj.calorietracker.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessages {

    EXCEPTION_VALIDATION_TYPE("Validation errors"),
    USER_NOT_FOUND("Could not find user with the specified id!"),
    USERNAME_NOT_FOUND("Could not find user with the specified username!"),
    UNIT_NOT_FOUND("Specified unit of measurement does not exist or it is not available for this food!"),
    FOOD_NOT_FOUND("Could not find food with the specified id!"),
    FOOD_ALREADY_DELETED("Food with this id is already deleted!"),
    DIARY_ENTRY_NOT_FOUND("Could not find a diary entry with the specified id!"),
    FOOD_BARCODE_CONFLICT("Food with this barcode already exists!"),
    FOOD_NAME_BRAND_CONFLICT("Food with this name and brand already exists!"),
    NO_ERROR_MESSAGE("Could not retrieve error message.");

    private final String text;
}
