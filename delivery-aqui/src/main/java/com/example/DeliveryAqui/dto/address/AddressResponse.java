package com.example.DeliveryAqui.dto.address;

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
