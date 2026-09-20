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
    List<PersonResponse> entityToResponse(List<Person> person);

    PersonDetailResponse entityToDetailResponse(Person person);
    List<PersonDetailResponse> entityToDetailResponse(List<Person> person);

    Person postRequestToEntity(PersonPostRequest personPostRequest);
    Person putRequestToEntity(PersonPutRequest personPutRequest);

    void updateEntity(
            PersonPutRequest request,
            @MappingTarget Person person
    );
}
