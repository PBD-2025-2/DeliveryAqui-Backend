package com.example.DeliveryAqui.dtos.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CategoryPostRequest(
        @NotNull(message = "storeId must not be empty")
        Long storeId,

        Long parentId,

        @NotBlank(message = "Name must not be empty")
        String name,

        @Positive(message = "Position must be greater than zero")
        @NotNull(message = "Position must not be empty")
        Integer position
) {}