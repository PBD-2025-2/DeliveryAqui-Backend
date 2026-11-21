package com.example.DeliveryAqui.dtos.extra;

import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupSummaryResponse;

import java.math.BigDecimal;

public record ExtraResponse(
        Long id,
        ExtraGroupSummaryResponse extraGroup,
        String name,
        BigDecimal price,
        Boolean isAvailable
) {
}
