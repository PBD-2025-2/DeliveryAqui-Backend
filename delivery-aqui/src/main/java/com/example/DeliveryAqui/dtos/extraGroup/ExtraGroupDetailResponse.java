package com.example.DeliveryAqui.dtos.extraGroup;

import com.example.DeliveryAqui.dtos.extra.ExtraSummaryResponse;

import java.util.List;

public record ExtraGroupDetailResponse(
        Long id,
        Long storeId,
        String name,
        Short minSelect,
        Short maxSelect,
        List<ExtraSummaryResponse> extras
) {
}
