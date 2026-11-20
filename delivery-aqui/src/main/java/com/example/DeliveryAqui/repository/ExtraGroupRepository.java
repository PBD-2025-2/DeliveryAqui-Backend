package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.ExtraGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExtraGroupRepository extends JpaRepository<ExtraGroup, Long> {
    @EntityGraph(attributePaths = "extras")
    Optional<ExtraGroup> findWithExtrasById(Long id);

    Page<ExtraGroup> findByStore_Id(Long storeId, Pageable pageable);
    Page<ExtraGroup> findByNameContainsIgnoreCase(String name, Pageable pageable);
}
