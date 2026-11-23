package com.example.DeliveryAqui.exception.store;

import com.example.DeliveryAqui.enums.ErrorType;
import com.example.DeliveryAqui.exception.ResourceNotFoundException;

public class StoreNotFoundException extends ResourceNotFoundException {
    private static final String NOT_FOUND_BY_ID = "store not found: %d";

    public StoreNotFoundException(Long id) {
        super(String.format(NOT_FOUND_BY_ID,id), ErrorType.STORE_NOT_FOUND.getUri());
    }
    public StoreNotFoundException(String cnpj) {
        super(String.format(NOT_FOUND_BY_ID,cnpj), ErrorType.STORE_NOT_FOUND.getUri());
    }
}
