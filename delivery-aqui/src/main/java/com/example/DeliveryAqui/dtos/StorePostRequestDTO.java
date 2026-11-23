package com.example.DeliveryAqui.dtos;

import com.example.DeliveryAqui.model.entity.Address;

public record StorePostRequestDTO(Address address, String name, String cnpj, String phoneNumber, String email) {
}
