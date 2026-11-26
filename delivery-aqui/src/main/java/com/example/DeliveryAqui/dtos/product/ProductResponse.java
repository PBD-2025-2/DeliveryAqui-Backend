package com.example.DeliveryAqui.dtos.product;

import com.example.DeliveryAqui.dtos.extra.ExtraSummaryResponse;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        Long id,
        Long storeId,
        Long categoryId,
        String name,
        String description,
        String photo,
        BigDecimal salePrice,
        BigDecimal costPrice,
        List<ExtraSummaryResponse> extras
) {}