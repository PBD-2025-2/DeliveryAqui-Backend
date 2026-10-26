package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.ExtraGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtraGroupRepository extends JpaRepository<ExtraGroup, Long> {
    Page<ExtraGroup> findByNameContainsIgnoreCase(String name, Pageable pageable);
}
