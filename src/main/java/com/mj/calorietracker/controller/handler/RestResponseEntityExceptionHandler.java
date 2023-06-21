package com.mj.calorietracker.controller.handler;

import com.mj.calorietracker.exception.FoodAlreadyExistsException;
import com.mj.calorietracker.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FoodAlreadyExistsException.class)
    public ResponseEntity<Object> handleFoodAlreadyExistsException(FoodAlreadyExistsException ex, HttpServletRequest request) {
        final var responseBody = new HashMap<>(Map.of(
                "title", "Validation Errors",
                "resource", request.getRequestURL(),
                "violationMessage", ex.getMessage(),
                "existingFood", ex.getExistingFood()));

        return new ResponseEntity<>(responseBody, null, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        final var responseBody = new HashMap<>(Map.of(
                "title", "Validation Errors",
                "resource", request.getRequestURL(),
                "violationMessage", ex.getMessage()));

        return new ResponseEntity<>(responseBody, null, HttpStatus.NOT_FOUND);
    }

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
