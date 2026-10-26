package com.example.DeliveryAqui.dtos.product;

public record ProductSummaryResponse(
        Long id,
        Long storeId,
        Long categoryId,
        String name
) {}