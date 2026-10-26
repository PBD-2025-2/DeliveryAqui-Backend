package com.example.DeliveryAqui.dtos.personAddress;

import java.time.Instant;

public record PersonAddressSummaryResponse(
        Long id,
        Long personId,
        Long addressId,
        Boolean isFavorite,
        Instant createdAt,
        Instant updatedAt
) {
}
