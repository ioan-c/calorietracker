package com.mj.calorietracker.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorInfoExtended extends ErrorInfo{
    private String field;
    private Object resource;
    private String resourceType;
    public ErrorInfoExtended(String message, String field) {
        super(message);
        this.field = field;
    }
    public ErrorInfoExtended(String message, Object resource) {
        super(message);
        this.resource = resource;
    }
    public ErrorInfoExtended(String message, String field, String resourceType) {
        super(message);
        this.field = field;
        this.resourceType = resourceType;
    }
}
