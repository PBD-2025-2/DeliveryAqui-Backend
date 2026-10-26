package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByStoreIdAndName(Long storeId, String categoryName);

    Page<Category> findByStoreId(Long storeId, Pageable pageable);
}
