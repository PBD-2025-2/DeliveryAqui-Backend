package com.example.DeliveryAqui.dtos.productExtra;

public record ProductExtraResponse(
        Long id,
        Long productId,
        Long extraId,
        Boolean isAvailable
) {}