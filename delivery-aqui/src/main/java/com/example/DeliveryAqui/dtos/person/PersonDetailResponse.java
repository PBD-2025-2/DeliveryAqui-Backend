package com.example.DeliveryAqui.dtos.person;

import java.time.Instant;

public record PersonDetailResponse(
        Long id,
        String cpf,
        String firstName,
        String lastName,
        String phoneNumber,
        Instant createdAt,
        Instant updatedAt
) {}
