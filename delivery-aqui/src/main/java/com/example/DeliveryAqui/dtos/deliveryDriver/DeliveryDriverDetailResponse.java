package com.example.DeliveryAqui.dtos.deliveryDriver;

import com.example.DeliveryAqui.dtos.customer.CustomerDetailResponse;

import java.math.BigDecimal;

public record DeliveryDriverDetailResponse(
        Long id,
        Boolean open,
        BigDecimal earnings,
        CustomerDetailResponse customer
) {}
