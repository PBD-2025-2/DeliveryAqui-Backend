package com.example.DeliveryAqui.dtos.productExtra;

import jakarta.validation.constraints.Positive;

public record ProductExtraPostRequest(
        @Positive(message = "Product ID must be greater than zero.")
        Long productId,

        @Positive(message = "Extra ID must be greater than zero.")
        Long extraId
) {
}
