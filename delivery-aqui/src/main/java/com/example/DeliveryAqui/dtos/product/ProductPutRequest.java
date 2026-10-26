package com.example.DeliveryAqui.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductPutRequest(
        @NotNull(message = "categoryId must not be empty")
        Long categoryId,

        @NotBlank(message = "Name must not be empty")
        String name,
        String description,
        String photo,
        BigDecimal salePrice,
        BigDecimal costPrice
) {
}
