package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRespository extends JpaRepository<OrderItem, Long> {

}
