package com.example.DeliveryAqui.exception;

import com.example.DeliveryAqui.enums.ErrorType;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ResourceNotFoundException extends ApiException {
    private static final String RESOURCE_NOT_FOUND_MESSAGE = "%s not found with ID: %s";
    private static final String RESOURCES_NOT_FOUND_MESSAGE = "%s not found with IDs: %s";

    public ResourceNotFoundException(String resourceName, Long id, ErrorType type) {
        super(String.format(RESOURCE_NOT_FOUND_MESSAGE, resourceName, id), HttpStatus.NOT_FOUND, type.getUri());
    }

    public ResourceNotFoundException(String resourceName, List<Long> id, ErrorType type) {
        super(String.format(RESOURCE_NOT_FOUND_MESSAGE, resourceName, id), HttpStatus.NOT_FOUND, type.getUri());
    }
}
