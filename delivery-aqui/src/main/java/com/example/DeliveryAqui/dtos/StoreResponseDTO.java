package com.example.DeliveryAqui.dtos;

import com.example.DeliveryAqui.model.entity.Address;
import com.example.DeliveryAqui.model.entity.Store;

public record StoreResponseDTO(Long id, Address address, String name, String cnpj, String phoneNumber, String email) {
    public static StoreResponseDTO fromEntity(Store store) {
        if (store == null) {
            return null;
        }
        return new StoreResponseDTO(
                store.getId(),
                store.getAddress(),
                store.getName(),
                store.getCnpj(),
                store.getPhoneNumber(),
                store.getEmail()
        );
    }

}
