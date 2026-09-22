package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.customer.CustomerDetailResponse;
import com.example.DeliveryAqui.dtos.customer.CustomerPostRequest;
import com.example.DeliveryAqui.dtos.customer.CustomerResponse;
import com.example.DeliveryAqui.mapper.CustomerMapper;
import com.example.DeliveryAqui.model.entity.Customer;
import com.example.DeliveryAqui.model.entity.DeliveryDriver;
import com.example.DeliveryAqui.model.entity.Person;
import com.example.DeliveryAqui.repository.CustomerRepository;
import com.example.DeliveryAqui.repository.DeliveryDriverRepository;
import com.example.DeliveryAqui.repository.PersonRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final PersonRepository personRepository;
    private final DeliveryDriverRepository deliveryDriverRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<CustomerResponse> findAll() {

        return customerRepository.findAll()
                .stream()
                .map(customerMapper::entityToResponse).toList();
    }

    public CustomerDetailResponse findById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));

        return customerMapper.entityToDetailResponse(customer);
    }

    public CustomerResponse create(CustomerPostRequest request) {
        Customer customer = customerRepository.findByPersonId(request.personId());
        if (customer != null)
            throw new EntityExistsException("Customer already exists");

        Person person = personRepository.findById(request.personId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id: " + request.personId()));

        customer = new Customer();
        customer.setPerson(person);
        customerRepository.save(customer);
        return customerMapper.entityToResponse(customer);
    }

    public void delete(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));

        DeliveryDriver deliveryDriver = deliveryDriverRepository.findByCustomerId(customer.getId());
        if (deliveryDriver != null)
            throw new IllegalArgumentException("Cannot delete Customer with id " + id + ". There is a DeliveryDriver related to it");

        customerRepository.delete(customer);
        personRepository.delete(customer.getPerson());
    }
}
