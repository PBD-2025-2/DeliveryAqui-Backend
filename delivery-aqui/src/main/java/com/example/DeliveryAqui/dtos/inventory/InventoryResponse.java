package com.example.DeliveryAqui.dtos.inventory;

public record InventoryResponse(
        Long id,
        Long productId,
        Integer quantity
) {
}