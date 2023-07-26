package com.mj.calorietracker.exception;

import lombok.Getter;

@Getter
public class ExistingResourceException extends RuntimeException {

    private final Object existingResource;

    public ExistingResourceException(Object existingResource, String message) {
        super(message);
        this.existingResource = existingResource;
    }
}
