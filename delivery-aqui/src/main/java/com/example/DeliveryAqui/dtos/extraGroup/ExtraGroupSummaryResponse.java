package com.example.DeliveryAqui.dtos.extraGroup;

public record ExtraGroupSummaryResponse(
        Long id,
        Long storeId,
        String name,
        Short minSelect,
        Short maxSelect
) {
}
