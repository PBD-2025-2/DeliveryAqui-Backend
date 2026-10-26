package com.example.DeliveryAqui.exception;

import com.example.DeliveryAqui.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class ResourceExistsException extends ApiException {
    private static final String RESOURCE_EXISTS_EXCEPTION = "%s already exists: %s";

    public ResourceExistsException(String resourceName, Object id, ErrorType type) {
        super(String.format(RESOURCE_EXISTS_EXCEPTION, resourceName, id), HttpStatus.CONFLICT, type.getUri());
    }
}
