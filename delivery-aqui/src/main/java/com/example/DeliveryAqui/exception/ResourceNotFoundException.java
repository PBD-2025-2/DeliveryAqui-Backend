package com.example.DeliveryAqui.exception;

import org.springframework.http.HttpStatus;

public abstract class ResourceNotFoundException extends ApiException {
    public ResourceNotFoundException(String message, String type) {
        super(message, HttpStatus.NOT_FOUND, type);
    }
}
