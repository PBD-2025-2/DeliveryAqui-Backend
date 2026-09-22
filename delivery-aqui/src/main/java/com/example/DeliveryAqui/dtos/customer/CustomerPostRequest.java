package com.example.DeliveryAqui.dtos.customer;

import jakarta.validation.constraints.NotNull;

public record CustomerPostRequest(
        @NotNull(message = "personId must not be blank")
        Long personId
) {}
