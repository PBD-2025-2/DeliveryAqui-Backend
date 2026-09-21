package com.example.DeliveryAqui.dtos.deliveryDriver;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeliveryDriverPostRequest(
        @NotNull(message = "CustomerId must not be empty")
        Long customerId
) {}
