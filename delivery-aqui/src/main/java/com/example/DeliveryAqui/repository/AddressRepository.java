package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Page<Address> findByAddressLine1ContainsIgnoreCase(String street, Pageable pageable);
    Page<Address> findByNeighborhoodContainsIgnoreCase(String neighborhood, Pageable pageable);
    Page<Address> findByCityContainsIgnoreCase(String city, Pageable pageable);
    Page<Address> findByStateContainsIgnoreCase(String state, Pageable pageable);
    Page<Address> findByPostalCodeContainsIgnoreCase(String postalCode, Pageable pageable);
}
