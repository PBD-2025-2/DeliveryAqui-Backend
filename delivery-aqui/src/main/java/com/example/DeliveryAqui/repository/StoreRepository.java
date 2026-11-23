package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {


    Page<Store> findByName(String name, Pageable pageable);

    Page<Store> findByNameContainsIgnoreCase(String name, Pageable pageable);

    Optional<Store> findByCnpj(String cnpj);

    Optional<Store> findByPhoneNumber(String phoneNumber);

    Optional<Store> findByEmail(String email);
}
