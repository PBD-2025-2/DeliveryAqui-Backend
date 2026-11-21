package com.example.DeliveryAqui.dtos.extra;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ExtraPostRequest(
        @NotNull(message = "Extra group ID is required")
        @Positive(message = "Extra group ID must be greater than zero.")
        Long extraGroupId,

        @NotBlank(message = "Name may not be empty.")
        @Size(max = 255)
        String name,

        @NotNull(message = "Price is required")
        @PositiveOrZero(message = "Price must be zero or greater.")
        @Digits(integer = 8, fraction = 2, message = "Price must have up to 8 integer digits and 2 decimal places.")
        BigDecimal price,

        Boolean isAvailable
) {
}
