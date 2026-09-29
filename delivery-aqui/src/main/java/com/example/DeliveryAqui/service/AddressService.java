package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.address.AddressPostRequest;
import com.example.DeliveryAqui.dtos.address.AddressPutRequest;
import com.example.DeliveryAqui.dtos.address.AddressResponse;
import com.example.DeliveryAqui.dtos.address.AddressSummaryResponse;
import com.example.DeliveryAqui.enums.ErrorType;
import com.example.DeliveryAqui.exception.ResourceInUseException;
import com.example.DeliveryAqui.exception.ResourceNotFoundException;
import com.example.DeliveryAqui.mapper.AddressMapper;
import com.example.DeliveryAqui.model.entity.Address;
import com.example.DeliveryAqui.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public Page<AddressSummaryResponse> findAll(Pageable pageable) {
        Page<Address> addresses = addressRepository.findAll(pageable);
        return addresses.map(addressMapper::entityToSummaryResponse);
    }

    public AddressResponse findById(Long id) {
        Optional<Address> address = addressRepository.findById(id);
        return addressMapper.entityToResponse(address
                .orElseThrow(() -> new ResourceNotFoundException("Address", id, ErrorType.ADDRESS_NOT_FOUND)));
    }

    public  Page<AddressSummaryResponse> findByAddressLine1(String street, Pageable pageable) {
        Page<Address> addresses = addressRepository.findByAddressLine1ContainsIgnoreCase(street, pageable);
        return addresses.map(addressMapper::entityToSummaryResponse);
    }

    public  Page<AddressSummaryResponse> findByNeighborhood(String neighborhood, Pageable pageable) {
        Page<Address> addresses = addressRepository.findByNeighborhoodContainsIgnoreCase(neighborhood, pageable);
        return addresses.map(addressMapper::entityToSummaryResponse);
    }

    public  Page<AddressSummaryResponse> findByCity(String city, Pageable pageable) {
        Page<Address> addresses = addressRepository.findByCityContainsIgnoreCase(city, pageable);
        return addresses.map(addressMapper::entityToSummaryResponse);
    }

    public  Page<AddressSummaryResponse> findByState(String state, Pageable pageable) {
        Page<Address> addresses = addressRepository.findByStateContainsIgnoreCase(state, pageable);
        return addresses.map(addressMapper::entityToSummaryResponse);
    }

    public  Page<AddressSummaryResponse> findByPostalCode(String postalCode, Pageable pageable) {
        Page<Address> addresses = addressRepository.findByPostalCodeContainsIgnoreCase(postalCode, pageable);
        return addresses.map(addressMapper::entityToSummaryResponse);
    }

    @Transactional
    public AddressResponse save(AddressPostRequest postResquest) {
        Address address = addressMapper.requestToEntity(postResquest);
        Address savedAddress = addressRepository.save(address);
        return addressMapper.entityToResponse(savedAddress);
    }

    @Transactional
    public AddressResponse update(Long id, AddressPutRequest addressPutRequest) {
        Address address = addressRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address", id, ErrorType.ADDRESS_NOT_FOUND));

        addressMapper.updateEntity(addressPutRequest, address);
        addressRepository.save(address);
        return addressMapper.entityToResponse(address);
    }

    @Transactional
    public void delete(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address", id, ErrorType.ADDRESS_NOT_FOUND));

        try {
            addressRepository.delete(address);
            addressRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new ResourceInUseException("Address", id, ErrorType.ADDRESS_IN_USE);
        }
    }
}
