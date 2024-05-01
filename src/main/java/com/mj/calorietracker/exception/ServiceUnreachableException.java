package com.mj.calorietracker.exception;

import lombok.Getter;

@Getter
public class ServiceUnreachableException extends RuntimeException {
    public ServiceUnreachableException(String message) {
        super(message);
    }
}
