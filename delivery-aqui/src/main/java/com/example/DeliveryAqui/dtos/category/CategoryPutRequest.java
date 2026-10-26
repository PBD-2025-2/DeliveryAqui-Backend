package com.example.DeliveryAqui.dtos.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryPutRequest(
        @NotNull(message = "parentId must not be empty")
        Long parentId,

        @NotBlank(message = "Name must not be empty")
        String name
) {}