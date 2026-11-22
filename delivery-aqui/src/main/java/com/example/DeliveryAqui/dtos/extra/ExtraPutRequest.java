package com.example.DeliveryAqui.dtos.extra;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ExtraPutRequest(
        @NotNull(message = "Extra group ID is required")
        @Positive(message = "Extra group ID must be greater than zero.")
        Long extraGroupId,

        @NotBlank(message = "Name may not be empty.")
        @Size(max = 255, message = "Name must have at most 255 characters")
        String name,

        @NotNull(message = "Price is required")
        @PositiveOrZero(message = "Price must be zero or greater.")
        @Digits(integer = 10, fraction = 2, message = "Price must have up to 10 integer digits and 2 decimal places.")
        BigDecimal price,

        @NotNull(message = "Availability is required.")
        Boolean isAvailable,

        @NotNull(message = "Minimum quantity is required.")
        @PositiveOrZero(message = "Minimum quantity must be zero or greater.")
        Short minQuantity,

        @NotNull(message = "Maximum quantity is required.")
        @PositiveOrZero(message = "Maximum quantity must be zero or greater.")
        Short maxQuantity
) {
        @JsonIgnore
        @AssertTrue(message = "Maximum quantity must be greater than or equal to minimum quantity")
        public boolean isQuantityRangeValid() {
                if (minQuantity == null || maxQuantity == null) {
                        return true;
                }

                return maxQuantity >= minQuantity;
        }
}
