package com.example.DeliveryAqui.dtos.personAddress;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PersonAddressPostRequest(
        @NotNull(message = "Person ID may not be null.")
        @Positive(message = "Person ID must be greater than zero.")
        Long personId,

        @NotNull(message = "Address ID may not be null.")
        @Positive(message = "Address ID must be greater than zero.")
        Long addressId,

        @NotNull(message = "Is favorite must not be null")
        Boolean isFavorite
) {
}
