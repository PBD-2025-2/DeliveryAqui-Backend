package com.example.DeliveryAqui.exception;

import org.springframework.http.HttpStatus;

public abstract class ResourceInUseException extends ApiException {
    public ResourceInUseException(String message, String type) {
        super(message, HttpStatus.CONFLICT, type);
    }
}
