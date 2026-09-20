package com.example.DeliveryAqui.dtos.person;

import java.time.Instant;

public record PersonDetailResponse(
        Long id,
        Long userId,
        String cpf,
        String firstName,
        String lastName,
        String phoneNumber,
        Instant createdAt,
        Instant updatedAt
) {}
