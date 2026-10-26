package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dtos.store.StoreResponse;
import com.example.DeliveryAqui.dtos.store.StoreSummaryResponse;
import com.example.DeliveryAqui.model.entity.Store;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoreMapper {

    @Mapping(source = "address.id", target = "addressId")
    StoreResponse entityToResponse(Store store);

    @Mapping(source = "address.id", target = "addressId")
    StoreSummaryResponse entityToSummaryResponse(Store store);

}
