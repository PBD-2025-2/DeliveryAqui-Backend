package com.example.DeliveryAqui.mapper;


import com.example.DeliveryAqui.dtos.store.StorePostRequest;
import com.example.DeliveryAqui.dtos.store.StorePutRequest;
import com.example.DeliveryAqui.dtos.store.StoreResponse;
import com.example.DeliveryAqui.dtos.store.StoreSummaryResponse;
import com.example.DeliveryAqui.model.entity.Store;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StoreMapper {

    StoreSummaryResponse entityToStoreSummaryResponse(Store store);

    StoreResponse entityToStoreResponse(Store store);

    Store responseToEntity (StorePostRequest storePostRequest);
    @Mapping(target = "id", ignore = true)
    void updateEntity(StorePutRequest storePutRequest, @MappingTarget Store store);
}
