package com.example.DeliveryAqui.dtos.inventory;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record InventoryPutRequest(
        @PositiveOrZero(message = "Quantity must be positive")
        Integer quantity
) {}