package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.ProductExtra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductExtraRepository extends JpaRepository<ProductExtra, Long> {
    List<ProductExtra> findAllByProduct_Id(Long productId);
}
