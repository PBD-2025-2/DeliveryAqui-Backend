package com.example.DeliveryAqui.exception;

import com.example.DeliveryAqui.enums.ErrorType;
import org.springframework.http.HttpStatus;

public class ResourceInUseException extends ApiException {
    private static final String RESOURCE_IN_USE_MESSAGE = "Cannot delete %s with id %d because it is in use.";

    public ResourceInUseException(String resourceName, Long id, ErrorType type) {
        super(String.format(RESOURCE_IN_USE_MESSAGE, resourceName, id), HttpStatus.CONFLICT, type.getUri());
    }
}
