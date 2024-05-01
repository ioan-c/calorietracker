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
    INGREDIENT_NOT_FOUND("Could not find ingredient with the specified id!"),
    FOOD_NOT_FOUND("Could not find food with the specified id!"),
    FOOD_BY_NAME_NOT_FOUND("Could not find current food corresponding to given name!"),
    FOOD_ALREADY_DELETED("Food with this id is already deleted!"),
    DIARY_ENTRY_NOT_FOUND("Could not find a diary entry with the specified id!"),
    FOOD_BARCODE_CONFLICT("Food with this barcode already exists!"),
    FOOD_NAME_BRAND_CONFLICT("Food with this name and brand already exists!"),
    FOOD_NAME_CONFLICT("Food with this name already exists!"),
    RECIPE_CONFLICT("Recipe already exists!"),
    RECIPE_NOT_FOUND("Could not find recipe with the specified id!"),
    NO_ERROR_MESSAGE("Could not retrieve error message."),
    USER_MANAGEMENT_NOT_REACHABLE("Could not reach user management service!"),
    ADDITIONAL_NOTE_ADD_INGREDIENTS("This occurred during the validation of added ingredients."),
    ADDITIONAL_NOTE_UPDATE_INGREDIENTS("This occurred during the validation of updated ingredients.");

    private final String text;
}
