package com.example.DeliveryAqui.exception.address;

import com.example.DeliveryAqui.enums.ErrorType;
import com.example.DeliveryAqui.exception.ResourceInUseException;

public class AddressInUseException extends ResourceInUseException {
    private static final String ADDRESS_IN_USE = "Cannot delete address with id %d because it is in use.";

    public AddressInUseException(Long id) {
        super(String.format(ADDRESS_IN_USE, id), ErrorType.ADDRESS_IN_USE.getUri());
    }
}
