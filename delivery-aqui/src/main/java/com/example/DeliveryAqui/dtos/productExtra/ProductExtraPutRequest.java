package com.example.DeliveryAqui.dtos.productExtra;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record ProductExtraPutRequest(
        @NotNull(message = "Extra IDs must be not null or empty")
        List<@NotNull(message = "Extra ID must be not null") @Positive(message = "Extra ID must be greater than zero.") Long> extraIds
) {
}
