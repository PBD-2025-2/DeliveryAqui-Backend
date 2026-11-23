package com.example.DeliveryAqui.dtos.store;

import java.time.Instant;

public record StoreResponse(
        Long id,
        String name,
        String cnpj,
        String phoneNumber,
        String email,
        Instant createdAt,
        Instant updatedAt
) {}
