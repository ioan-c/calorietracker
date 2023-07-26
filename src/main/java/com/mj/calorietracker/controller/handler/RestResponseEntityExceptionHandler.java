package com.mj.calorietracker.controller.handler;

import com.mj.calorietracker.exception.ConflictException;
import com.mj.calorietracker.exception.ExistingResourceException;
import com.mj.calorietracker.exception.ResourceNotFoundException;
import com.mj.calorietracker.exception.model.ErrorInfo;
import com.mj.calorietracker.exception.model.ExceptionResponse;
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

import static com.mj.calorietracker.enums.ExceptionMessages.EXCEPTION_VALIDATION_TYPE;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<ExceptionResponse> handleConflictException(RuntimeException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                EXCEPTION_VALIDATION_TYPE.getText(),
                request.getRequestURL().toString(),
                ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, null, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExistingResourceException.class)
    public ResponseEntity<ExceptionResponse> handleExistingResourceException(ExistingResourceException ex, HttpServletRequest request) {
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage()).setResource(ex.getExistingResource());
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                EXCEPTION_VALIDATION_TYPE.getText(),
                request.getRequestURL().toString(),
                Collections.singletonList(errorInfo));

        return new ResponseEntity<>(exceptionResponse, null, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                EXCEPTION_VALIDATION_TYPE.getText(),
                request.getRequestURL().toString(),
                ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, null, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        var r = (ServletWebRequest) request;

        List<ErrorInfo> errorInfoList = ex.getBindingResult().getAllErrors().stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> {
                    FieldError fieldError = (FieldError) error;
                    String className = fieldError.getObjectName();
                    String fieldName = fieldError.getField();
                    String message = fieldError.getDefaultMessage();
                    String errorMessage = Objects.nonNull(message) ? message : "Generic error.";

                    return new ErrorInfo(errorMessage).setResourceType(className).setField(fieldName);
                })
                .toList();

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                EXCEPTION_VALIDATION_TYPE.getText(),
                r.getRequest().getRequestURL().toString(),
                errorInfoList);

        return new ResponseEntity<>(exceptionResponse, null, status);
    }
}
