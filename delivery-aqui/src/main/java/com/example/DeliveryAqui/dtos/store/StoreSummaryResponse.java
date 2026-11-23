package com.example.DeliveryAqui.dtos.store;

import java.time.Instant;

public record StoreSummaryResponse(
        Long id,
        String name,
        Instant updatedAt
) {}
