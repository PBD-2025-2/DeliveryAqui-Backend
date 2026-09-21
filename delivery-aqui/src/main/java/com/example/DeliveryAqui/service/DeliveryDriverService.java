package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.deliveryDriver.DeliveryDriverDetailResponse;
import com.example.DeliveryAqui.dtos.deliveryDriver.DeliveryDriverPostRequest;
import com.example.DeliveryAqui.dtos.deliveryDriver.DeliveryDriverResponse;
import com.example.DeliveryAqui.mapper.CustomerMapper;
import com.example.DeliveryAqui.mapper.DeliveryDriverMapper;
import com.example.DeliveryAqui.model.entity.Customer;
import com.example.DeliveryAqui.model.entity.DeliveryDriver;
import com.example.DeliveryAqui.repository.CustomerRepository;
import com.example.DeliveryAqui.repository.DeliveryDriverRepository;
import com.example.DeliveryAqui.repository.PersonRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DeliveryDriverService {

    private final PersonRepository personRepository;
    private final DeliveryDriverRepository deliveryDriverRepository;
    private final CustomerRepository customerRepository;
    private final DeliveryDriverMapper deliveryDriverMapper;
    private final CustomerMapper customerMapper;

    public List<DeliveryDriverResponse> findAll() {

        return deliveryDriverRepository.findAll()
                .stream()
                .map(deliveryDriverMapper::entityToResponse).toList();
    }

    public DeliveryDriverDetailResponse findById(Long id) {
        DeliveryDriver deliveryDriver = deliveryDriverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DeliveryDriver not found with id: " + id));

        return deliveryDriverMapper.entityToDetailResponse(deliveryDriver);
    }

    public DeliveryDriverResponse create(DeliveryDriverPostRequest request) {
        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + request.customerId()));

        DeliveryDriver deliveryDriver = deliveryDriverRepository.findByCustomerId(customer.getId());
        if (deliveryDriver != null)
            throw new EntityExistsException("DeliveryDriver already exists");

        deliveryDriver = new DeliveryDriver();
        deliveryDriver.setCustomer(customer);
        deliveryDriver.setOpen(false);
        deliveryDriver.setEarnings(BigDecimal.ZERO);
        deliveryDriverRepository.save(deliveryDriver);

        return deliveryDriverMapper.entityToResponse(deliveryDriver);
    }

    public DeliveryDriverResponse changeWorkMode(Long id, boolean mode) {
        DeliveryDriver deliveryDriver = deliveryDriverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DeliveryDriver not found with id: " + id));

        deliveryDriver.setOpen(mode);
        deliveryDriverRepository.save(deliveryDriver);
        return deliveryDriverMapper.entityToResponse(deliveryDriver);
    }

    public void delete(Long id) {
        DeliveryDriver deliveryDriver = deliveryDriverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DeliveryDriver not found with id: " + id));

        deliveryDriverRepository.delete(deliveryDriver);
        customerRepository.delete(deliveryDriver.getCustomer());
        personRepository.delete(deliveryDriver.getCustomer().getPerson());
    }
}
