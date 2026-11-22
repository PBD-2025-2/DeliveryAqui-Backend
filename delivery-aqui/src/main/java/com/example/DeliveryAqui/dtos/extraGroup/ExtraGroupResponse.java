package com.example.DeliveryAqui.dtos.extraGroup;

import com.example.DeliveryAqui.dtos.extra.ExtraSummaryResponse;
import com.example.DeliveryAqui.dtos.store.StoreSummaryResponse;

import java.util.List;

public record ExtraGroupResponse(
        Long id,
        StoreSummaryResponse store,
        String name,
        Short minSelect,
        Short maxSelect,
        List<ExtraSummaryResponse> extras
) {
}
