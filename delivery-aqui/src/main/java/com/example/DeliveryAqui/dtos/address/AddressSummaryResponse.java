package com.example.DeliveryAqui.dtos.address;

import java.time.Instant;

public record AddressSummaryResponse(
        Long id,
        String addressLine1,
        String city,
        String state,
        Instant updatedAt
) {
}
