package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.Category;
import com.example.DeliveryAqui.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryId(Long id, Pageable pageable);
    Page<Product> findByStoreId(Long id, Pageable pageable);
    Page<Product> findByStoreIdAndCategoryId(Long storeId, Long categoryId, Pageable pageable);
}
