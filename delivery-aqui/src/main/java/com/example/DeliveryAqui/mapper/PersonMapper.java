package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dtos.person.PersonDetailResponse;
import com.example.DeliveryAqui.dtos.person.PersonPostRequest;
import com.example.DeliveryAqui.dtos.person.PersonPutRequest;
import com.example.DeliveryAqui.dtos.person.PersonResponse;
import com.example.DeliveryAqui.model.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    PersonResponse entityToResponse(Person person);

    PersonDetailResponse entityToDetailResponse(Person person);

    Person postRequestToEntity(PersonPostRequest personPostRequest);

    void updateEntity(
            PersonPutRequest request,
            @MappingTarget Person person
    );
}
