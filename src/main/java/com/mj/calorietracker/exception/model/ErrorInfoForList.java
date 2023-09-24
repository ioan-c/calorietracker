package com.mj.calorietracker.exception.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorInfoForList extends ErrorInfo {
    private Integer localId;
    private Integer index;
    private Object resource;
    public ErrorInfoForList(String message, Integer localId, Integer index) {
        super(message);
        this.localId = localId;
        this.index = index;
    }
    public ErrorInfoForList(String message, Integer localId, Integer index, Object resource) {
        super(message);
        this.localId = localId;
        this.index = index;
        this.resource = resource;
    }
}
