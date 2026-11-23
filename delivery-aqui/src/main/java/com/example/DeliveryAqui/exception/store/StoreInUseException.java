package com.example.DeliveryAqui.exception.store;

import com.example.DeliveryAqui.enums.ErrorType;
import com.example.DeliveryAqui.exception.ResourceInUseException;

public class StoreInUseException extends ResourceInUseException {
    private static final String STORE_IN_USE = "Cannot delete store with id %d because it is in use.";

    public StoreInUseException(Long id) {
        super(String.format(STORE_IN_USE, id), ErrorType.STORE_IN_USE.getUri());
    }
}
