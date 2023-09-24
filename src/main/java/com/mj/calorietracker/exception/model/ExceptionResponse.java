package com.mj.calorietracker.exception.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {
    private String type;
    private String url;
    private List<? extends ErrorInfo> errors;

    public ExceptionResponse(String type, String url, String message) {
        this.type = type;
        this.url = url;
        this.errors = Collections.singletonList(new ErrorInfo(message));
    }
}
