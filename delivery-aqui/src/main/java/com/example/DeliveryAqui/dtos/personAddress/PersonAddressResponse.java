package com.example.DeliveryAqui.dtos.personAddress;

import com.example.DeliveryAqui.dtos.address.AddressResponse;
import com.example.DeliveryAqui.dtos.person.PersonDetailResponse;

import java.time.Instant;

public record PersonAddressResponse(
        Long id,
        PersonDetailResponse person,
        AddressResponse address,
        Boolean isFavorite,
        Instant updatedAt
) {
}
