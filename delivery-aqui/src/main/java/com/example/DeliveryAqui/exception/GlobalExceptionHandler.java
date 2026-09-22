package com.example.DeliveryAqui.exception;

import com.example.DeliveryAqui.enums.ErrorType;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ProblemResponse handleApiException(ApiException e) {
        return new ProblemResponse(e.getMessage(), e.getStatus(), e.getType());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemResponse handleValidation(MethodArgumentNotValidException e) {
        Map<String, List<String>> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        return new ProblemResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed.",
                ErrorType.VALIDATION_ERROR.getUri(),
                errors);
    }
}
