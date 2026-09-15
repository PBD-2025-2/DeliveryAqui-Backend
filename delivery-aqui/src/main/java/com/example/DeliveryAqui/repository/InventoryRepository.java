package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
