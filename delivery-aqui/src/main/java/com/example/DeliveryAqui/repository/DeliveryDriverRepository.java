package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.DeliveryDriver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryDriverRepository extends JpaRepository<DeliveryDriver, Long> {
    DeliveryDriver findByCustomerId(Long id);
}
