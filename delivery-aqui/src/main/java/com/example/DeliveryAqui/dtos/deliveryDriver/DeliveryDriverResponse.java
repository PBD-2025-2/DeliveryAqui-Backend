package com.example.DeliveryAqui.dtos.deliveryDriver;

public record DeliveryDriverResponse(
        Long id,
        Long customerId,
        Boolean open
) {}
