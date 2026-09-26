package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.personAddress.PersonAddressPostRequest;
import com.example.DeliveryAqui.dtos.personAddress.PersonAddressPutRequest;
import com.example.DeliveryAqui.dtos.personAddress.PersonAddressResponse;
import com.example.DeliveryAqui.dtos.personAddress.PersonAddressSummaryResponse;
import com.example.DeliveryAqui.mapper.PersonAddressMapper;
import com.example.DeliveryAqui.model.entity.Address;
import com.example.DeliveryAqui.model.entity.Person;
import com.example.DeliveryAqui.model.entity.PersonAddress;
import com.example.DeliveryAqui.repository.AddressRepository;
import com.example.DeliveryAqui.repository.PersonAddressRepository;
import com.example.DeliveryAqui.repository.PersonRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonAddressService {
    private final PersonAddressRepository personAddressRepository;
    private final PersonAddressMapper personAddressMapper;
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    public List<PersonAddressSummaryResponse> findAll() {
        return personAddressRepository.findAll()
                .stream()
                .map(personAddressMapper::entityToSummaryResponse).toList();
    }

    public PersonAddressResponse findById(Long id) {
        PersonAddress personAddress = personAddressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with the ID address: " + id));

        return personAddressMapper.entityToResponse(personAddress);
    }

    public List<PersonAddressSummaryResponse> findByCpf(String cpf) {
        return personAddressRepository.findByPerson_Cpf(cpf)
                .stream()
                .map(personAddressMapper::entityToSummaryResponse).toList();
    }

    @Transactional
    public PersonAddressResponse save(@NonNull PersonAddressPostRequest request) {
        Person person = personRepository.findById(request.personId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        Address address = addressRepository.findById(request.addressId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        if (personAddressRepository.existsByPerson_IdAndAddress_Id(request.personId(), request.addressId())) {
            throw  new EntityExistsException("This address already exists for this person.");
        }

        boolean isFirstAddress = personAddressRepository.countByPerson_Id(person.getId()) == 0;

        PersonAddress personAddress = personAddressMapper.requestToEntity(request);
        personAddress.setPerson(person);
        personAddress.setAddress(address);

        if (isFirstAddress || Boolean.TRUE.equals(request.isFavorite())) {
            markAsFavorite(personAddress);
        }

        personAddressRepository.save(personAddress);
        return personAddressMapper.entityToResponse(personAddress);
    }

    @Transactional
    public PersonAddressResponse update(Long id, @NonNull PersonAddressPutRequest request) {
        PersonAddress personAddress = personAddressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person address not found."));

        Person person = personRepository.findById(request.personId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        Address address = addressRepository.findById(request.addressId())
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));

        personAddressMapper.updateEntity(request, personAddress);
        personAddress.setPerson(person);
        personAddress.setAddress(address);

        personAddressRepository.saveAndFlush(personAddress);
        return personAddressMapper.entityToResponse(personAddress);
    }

    @Transactional
    public PersonAddressResponse switchFavorite(Long id) {
        PersonAddress personAddress = personAddressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person address not found."));

        if (Boolean.TRUE.equals(personAddress.getIsFavorite())) {
            return personAddressMapper.entityToResponse(personAddress);
        }

        markAsFavorite(personAddress);

        personAddressRepository.saveAndFlush(personAddress);
        return personAddressMapper.entityToResponse(personAddress);
    }

    private void markAsFavorite(@NonNull PersonAddress personAddress) {
        clearFavorite(personAddress.getPerson().getId());
        personAddress.setIsFavorite(true);
    }

    private void clearFavorite(Long personId) {
        personAddressRepository.findByPerson_IdAndIsFavoriteTrue(personId)
                .ifPresent(personAddress -> {
                    personAddress.setIsFavorite(false);
                    personAddressRepository.flush();
                });
    }
}
