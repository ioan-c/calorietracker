package com.mj.calorietracker.controller.handler;

import com.mj.calorietracker.exception.*;
import com.mj.calorietracker.exception.model.ErrorInfoExtended;
import com.mj.calorietracker.exception.model.ErrorInfoForList;
import com.mj.calorietracker.exception.model.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSourceResolvable;
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
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mj.calorietracker.enums.ExceptionMessages.EXCEPTION_VALIDATION_TYPE;
import static com.mj.calorietracker.enums.ExceptionMessages.NO_ERROR_MESSAGE;

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
        ErrorInfoExtended errorInfo = new ErrorInfoExtended(ex.getMessage(), ex.getExistingResource());
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                EXCEPTION_VALIDATION_TYPE.getText(),
                request.getRequestURL().toString(),
                Collections.singletonList(errorInfo));

        return new ResponseEntity<>(exceptionResponse, null, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExistingResourcesException.class)
    public ResponseEntity<ExceptionResponse> handleResourcesNotFoundException(ExistingResourcesException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                EXCEPTION_VALIDATION_TYPE.getText(),
                request.getRequestURL().toString(),
                ex.getErrorInfoList());

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

    @ExceptionHandler(ResourcesNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourcesNotFoundException(ResourcesNotFoundException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                EXCEPTION_VALIDATION_TYPE.getText(),
                request.getRequestURL().toString(),
                ex.getErrorInfoList());

        return new ResponseEntity<>(exceptionResponse, null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConstraintViolationException(ConstraintViolationException constraintViolationException, WebRequest request) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();

        List<ErrorInfoExtended> errorInfoList = violations.stream()
                .map(violation -> {
                    String propertyPath = violation.getPropertyPath().toString();
                    String fieldName = propertyPath.substring(propertyPath.indexOf('.') + 1);
                    String message = violation.getMessage();
                    String errorMessage = Objects.nonNull(message) ? message : NO_ERROR_MESSAGE.getText();

                    return new ErrorInfoExtended(errorMessage, fieldName);
                }).toList();

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                EXCEPTION_VALIDATION_TYPE.getText(),
                ((ServletWebRequest) request).getRequest().getRequestURL().toString(),
                errorInfoList);

        return new ResponseEntity<>(exceptionResponse, null, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        var r = (ServletWebRequest) request;

        List<ErrorInfoExtended> errorInfoList = ex.getBindingResult().getAllErrors().stream()
                .filter(FieldError.class::isInstance)
                .map(error -> {
                    FieldError fieldError = (FieldError) error;
                    String className = fieldError.getObjectName();
                    String fieldName = fieldError.getField();
                    String message = fieldError.getDefaultMessage();
                    String errorMessage = Objects.nonNull(message) ? message : NO_ERROR_MESSAGE.getText();

                    return new ErrorInfoExtended(errorMessage, fieldName, className);
                })
                .toList();

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                EXCEPTION_VALIDATION_TYPE.getText(),
                r.getRequest().getRequestURL().toString(),
                errorInfoList);

        return new ResponseEntity<>(exceptionResponse, null, status);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var r = (ServletWebRequest) request;
        List<ErrorInfoForList> errorInfos = ex.getValueResults().stream().map(vr -> {
            Integer index = vr.getMethodParameter().getParameterIndex();
            String validationMessage = vr.getResolvableErrors().stream().map(MessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
            return new ErrorInfoForList(validationMessage, index);
        }).toList();
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                EXCEPTION_VALIDATION_TYPE.getText(),
                r.getRequest().getRequestURL().toString(),
                errorInfos);
        return new ResponseEntity<>(exceptionResponse, headers, status);
    }

    @ExceptionHandler(ServiceUnreachableException.class)
    public ResponseEntity<ExceptionResponse> handleExternalServiceUnreachable(ServiceUnreachableException ex, HttpServletRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                "Server Error",
                request.getRequestURL().toString(),
                ex.getMessage());

        return new ResponseEntity<>(exceptionResponse, null, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
