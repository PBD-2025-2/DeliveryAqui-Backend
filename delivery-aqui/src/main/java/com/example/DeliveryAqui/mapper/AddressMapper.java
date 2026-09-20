package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dto.address.AddressPostResquest;
import com.example.DeliveryAqui.dto.address.AddressPutRequest;
import com.example.DeliveryAqui.dto.address.AddressResponse;
import com.example.DeliveryAqui.model.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    List<AddressResponse> toResponseList(List<Address> addresses);
    AddressResponse toResponse(Address address);
    Address toEntity(AddressPostResquest request);
    void updateEntity(AddressPutRequest putRequest, @MappingTarget Address address);
}