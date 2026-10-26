package com.example.DeliveryAqui.dtos.address;

import java.time.Instant;

public record AddressResponse(
        Long id,
        String addressLine1,
        String addressLine2,
        String neighborhood,
        String city,
        String state,
        String postalCode,
        Integer number,
        String landmark,
        Instant createdAt,
        Instant updatedAt
) {
}
