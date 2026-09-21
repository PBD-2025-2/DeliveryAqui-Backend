package com.example.DeliveryAqui.dtos.customer;

import com.example.DeliveryAqui.dtos.person.PersonDetailResponse;

public record CustomerDetailResponse(
        Long id,
        PersonDetailResponse person
) {}
