package com.example.DeliveryAqui.dtos.inventory;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record InventoryPostRequest(
        @NotNull(message = "ProductId must not be empty")
        Long productId,

        @PositiveOrZero(message = "Quantity must be positive")
        Integer quantity
) {}