package com.example.DeliveryAqui.dtos.store;

public record StoreSummaryResponse(
        Long id,
        Long addressId,
        String name,
        String phoneNumber
) {}
