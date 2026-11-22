package com.example.DeliveryAqui.dtos.extra;

import java.math.BigDecimal;

public record ExtraSummaryResponse(
        Long id,
        Long extraGroupId,
        String name,
        BigDecimal price,
        Boolean isAvailable,
        Short minQuantity,
        Short maxQuantity
) {
}
