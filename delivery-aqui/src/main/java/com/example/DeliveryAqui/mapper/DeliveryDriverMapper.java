package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dtos.deliveryDriver.DeliveryDriverDetailResponse;
import com.example.DeliveryAqui.dtos.deliveryDriver.DeliveryDriverResponse;
import com.example.DeliveryAqui.model.entity.DeliveryDriver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DeliveryDriverMapper {

    @Mapping(source = "customer.id", target = "customerId")
    DeliveryDriverResponse entityToResponse(DeliveryDriver deliveryDriver);

    @Mapping(source = "customer", target = "customer")
    DeliveryDriverDetailResponse entityToDetailResponse(DeliveryDriver deliveryDriver);
}
