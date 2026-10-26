package com.example.DeliveryAqui.dtos.extraGroup;

public record ExtraGroupResponse(
        Long id,
        Long storeId,
        String name,
        Short minSelect,
        Short maxSelect
) {
}
