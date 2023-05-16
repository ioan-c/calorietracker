package com.mj.calorietracker.controller.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){

        var r = (ServletWebRequest) request;
        final var responseBody = new HashMap<String, Object>(Map.of("title", "Validation Errors", "resource", r.getRequest().getRequestURL()));
        final var errorInfoList = new ArrayList<>();

        for (final var error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError fieldError) {
            String className = fieldError.getObjectName();
            String fieldName = fieldError.getField();
            String message = fieldError.getDefaultMessage();

            Map<String, String> errorInfo = Map.of("class", className, "field", fieldName, "violationMessage", Objects.nonNull(message) ? message : "Generic error.");
            errorInfoList.add(errorInfo);
            }

        }

        responseBody.put("errors", errorInfoList);
        return new ResponseEntity<>(responseBody, null, status);
    }
}
