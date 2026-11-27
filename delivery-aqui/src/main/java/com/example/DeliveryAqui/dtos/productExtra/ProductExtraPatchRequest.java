package com.example.DeliveryAqui.dtos.productExtra;

import jakarta.validation.constraints.NotNull;

public record ProductExtraPatchRequest(
        @NotNull Boolean isAvailable
) {
}
