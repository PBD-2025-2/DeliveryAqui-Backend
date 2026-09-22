package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dtos.customer.CustomerDetailResponse;
import com.example.DeliveryAqui.dtos.customer.CustomerResponse;
import com.example.DeliveryAqui.model.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = PersonMapper.class)
public interface CustomerMapper {


    @Mapping(source = "person.id", target = "personId")
    @Mapping(source = "person.firstName", target = "firstName")
    CustomerResponse entityToResponse(Customer customer);

    @Mapping(source = "person", target = "person")
    CustomerDetailResponse entityToDetailResponse(Customer customer);
}
