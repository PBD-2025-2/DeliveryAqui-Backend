package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
