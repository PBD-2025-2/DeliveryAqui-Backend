package com.example.DeliveryAqui.dtos.store;

public record StoreResponse (
        Long id,
        Long addressId,
        String name,
        String phone,
        String cnpj,
        String email,
        String phoneNumber
) {}
