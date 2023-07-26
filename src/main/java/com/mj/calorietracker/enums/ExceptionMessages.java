package com.mj.calorietracker.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessages {

    EXCEPTION_VALIDATION_TYPE("Validation errors"),
    USER_NOT_FOUND("Could not find user with the specified id!"),
    USERNAME_NOT_FOUND("Could not find user with the specified username!"),
    UNIT_NOT_FOUND("Could not find unit of measurement with the specified id!"),
    FOOD_NOT_FOUND("Could not find food with the specified id!"),
    FOOD_ALREADY_DELETED("Food with this id is already deleted!"),
    DIARY_ENTRY_NOT_FOUND("Could not find a diary entry with the specified id!"),
    DIARY_FOOD_COULD_NOT_BE_SAVED("Food could not be saved! Proceeding to log in diary only!"),
    FOOD_BARCODE_CONFLICT("Food with this barcode already exists!"),
    FOOD_NAME_BRAND_CONFLICT("Food with this name and brand already exists!");

    private final String text;
}
