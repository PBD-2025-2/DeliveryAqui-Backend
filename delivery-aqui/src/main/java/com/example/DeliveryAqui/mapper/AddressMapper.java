package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dtos.address.AddressPostRequest;
import com.example.DeliveryAqui.dtos.address.AddressPutRequest;
import com.example.DeliveryAqui.dtos.address.AddressResponse;
import com.example.DeliveryAqui.dtos.address.AddressSummaryResponse;
import com.example.DeliveryAqui.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResponse entityToResponse(Address address);
    List<AddressSummaryResponse> entityListToResponse(List<Address> addresses);

    Address requestToEntity(AddressPostRequest request);
    void updateEntity(AddressPutRequest putRequest, @MappingTarget Address address);
}