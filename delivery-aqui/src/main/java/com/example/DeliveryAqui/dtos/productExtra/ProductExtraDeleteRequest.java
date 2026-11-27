package com.example.DeliveryAqui.dtos.productExtra;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record ProductExtraDeleteRequest(
        @NotEmpty(message = "List of Extra IDs must not be null nor empty.")
        List<@NotNull(message = "Extra ID must be not null") @Positive(message = "Extra ID must be greater than zero.")Long> extraIds
) {
}
