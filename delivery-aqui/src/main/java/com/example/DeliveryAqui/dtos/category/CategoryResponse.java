package com.example.DeliveryAqui.dtos.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryResponse(
        Long id,
        Long storeId,
        Long parentId,
        String name,
        Integer position,
        String path
) {
}