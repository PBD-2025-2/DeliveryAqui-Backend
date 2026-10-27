package com.example.DeliveryAqui.dtos.extraGroup;

import com.example.DeliveryAqui.dtos.store.StoreResponse;

public record ExtraGroupResponse(
        Long id,
        StoreResponse store,
        String name,
        Short minSelect,
        Short maxSelect
) {
}
