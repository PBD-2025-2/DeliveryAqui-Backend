package com.example.DeliveryAqui.dtos.store;

import com.example.DeliveryAqui.dtos.address.AddressSummaryResponse;

public record StoreResponse (
        Long id,
        AddressSummaryResponse address,
        String name,
        String cnpj,
        String email,
        String phoneNumber
) {}
