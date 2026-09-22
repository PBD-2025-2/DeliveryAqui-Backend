package com.example.DeliveryAqui.dtos.address;

public record AddressSummaryResponse(
        Long id,
        String addressLine1,
        String city,
        String state
) {
}
