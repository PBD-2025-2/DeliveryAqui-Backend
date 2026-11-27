package com.example.DeliveryAqui.dtos.product;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductPutRequest(
        @NotNull(message = "Category ID must not be null")
        @Positive(message = "Category ID must be greater than zero")
        Long categoryId,

        @NotBlank(message = "Name must not be empty")
        @Size(max = 255, message = "Name must contain at most 100 characters")
        String name,

        @Size(max = 255, message = "Description must contain at most 500 characters")
        String description,

        @Size(max = 500, message = "Photo URL must contain at most 500 characters")
        String photo,

        @NotNull(message = "Sale price must not be null")
        @Digits(integer = 10, fraction = 2, message = "Sale price must have at most 8 integer digits and 2 decimal places")
        BigDecimal salePrice,

        @NotNull(message = "Cost price must not be null")
        @Digits(integer = 10, fraction = 2, message = "Cost price must have at most 8 integer digits and 2 decimal places")
        BigDecimal costPrice,

        boolean isAvailable
) {
}
