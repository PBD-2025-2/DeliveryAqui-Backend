package com.example.DeliveryAqui.dtos.product;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        Long storeId,
        Long categoryId,
        String name,
        String description,
        String photo,
        BigDecimal salePrice,
        BigDecimal costPrice
) {}