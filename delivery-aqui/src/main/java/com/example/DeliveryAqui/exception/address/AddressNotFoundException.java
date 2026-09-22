package com.example.DeliveryAqui.exception.address;

import com.example.DeliveryAqui.enums.ErrorType;
import com.example.DeliveryAqui.exception.ResourceNotFoundException;

public class AddressNotFoundException extends ResourceNotFoundException {
    private static final String NOT_FOUND_BY_ID = "Address not found: %d";

    public AddressNotFoundException(Long id) {
        super(String.format(NOT_FOUND_BY_ID,id), ErrorType.ADDRESS_NOT_FOUND.getUri());
    }
}
