package com.example.DeliveryAqui.dtos.address;

public record AddressResponse(
        Long id,
        String addressLine1,
        String addressLine2,
        String neighborhood,
        String city,
        String state,
        String postalCode,
        Integer number
) {
}
