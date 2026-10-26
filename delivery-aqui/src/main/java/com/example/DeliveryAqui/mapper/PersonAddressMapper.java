package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dtos.personAddress.PersonAddressPostRequest;
import com.example.DeliveryAqui.dtos.personAddress.PersonAddressPutRequest;
import com.example.DeliveryAqui.dtos.personAddress.PersonAddressResponse;
import com.example.DeliveryAqui.dtos.personAddress.PersonAddressSummaryResponse;
import com.example.DeliveryAqui.model.entity.PersonAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PersonAddressMapper {

    @Mapping(source = "person", target = "person")
    @Mapping(source = "address", target = "address")
    PersonAddressResponse entityToResponse(PersonAddress personAddress);

    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "address.id", target = "addressId")
    PersonAddressSummaryResponse entityToSummaryResponse(PersonAddress personAddresses);

    PersonAddress requestToEntity(PersonAddressPostRequest request);
    void updateEntity(PersonAddressPutRequest putRequest, @MappingTarget PersonAddress address);
}
