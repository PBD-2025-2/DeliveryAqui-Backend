package com.example.DeliveryAqui.dtos.category;

public record CategorySummaryResponse(
        Long id,
        Long storeId,
        Long parentId,
        String name,
        String path
) {
}