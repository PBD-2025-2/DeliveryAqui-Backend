package com.example.DeliveryAqui.dtos.extraGroup;

import com.example.DeliveryAqui.dtos.extra.ExtraSummaryResponse;
import com.example.DeliveryAqui.dtos.store.StoreResponse;

import java.util.List;

public record ExtraGroupResponse(
        Long id,
        StoreResponse store,
        String name,
        Short minSelect,
        Short maxSelect,
        List<ExtraSummaryResponse> extras
) {
}
