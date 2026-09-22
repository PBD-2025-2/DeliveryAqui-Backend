package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findByPersonId(Long id);
}
