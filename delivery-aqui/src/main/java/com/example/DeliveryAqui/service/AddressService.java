package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dto.address.AddressPostResquest;
import com.example.DeliveryAqui.dto.address.AddressPutRequest;
import com.example.DeliveryAqui.dto.address.AddressResponse;
import com.example.DeliveryAqui.exception.address.AddressInUseException;
import com.example.DeliveryAqui.exception.address.AddressNotFoundException;
import com.example.DeliveryAqui.mapper.AddressMapper;
import com.example.DeliveryAqui.model.entity.Address;
import com.example.DeliveryAqui.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public List<AddressResponse> findAll() {
        List<Address> addresses = addressRepository.findAll();
        return addressMapper.toResponseList(addresses);
    }

    public AddressResponse findById(Long id) throws AddressNotFoundException{
        Optional<Address> address = addressRepository.findById(id);
        return addressMapper.toResponse(address
                .orElseThrow(() -> new AddressNotFoundException(id)));
    }

    @Transactional
    public AddressResponse save(AddressPostResquest postResquest) {
        Address address = addressMapper.toEntity(postResquest);
        Address savedAddress = addressRepository.save(address);
        return addressMapper.toResponse(savedAddress);
    }

    @Transactional
    public AddressResponse update(Long id, AddressPutRequest addressPutRequest) {
        Address address = addressRepository.findById(id).orElseThrow(
                () -> new AddressNotFoundException(id));

        addressMapper.updateEntity(addressPutRequest, address);
        addressRepository.save(address);
        return addressMapper.toResponse(address);
    }

    @Transactional
    public void delete(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));

        try {
            addressRepository.delete(address);
            addressRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new AddressInUseException(id);
        }
    }
}
