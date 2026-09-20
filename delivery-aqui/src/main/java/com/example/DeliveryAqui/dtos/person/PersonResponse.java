package com.example.DeliveryAqui.dtos.person;

import java.time.Instant;

public record PersonResponse(
        Long id,
        Long userId,
        Instant updatedAt
) {}
