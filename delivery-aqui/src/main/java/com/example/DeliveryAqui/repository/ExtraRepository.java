package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.Extra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExtraRepository extends JpaRepository<Extra, Long> {
    Page<Extra> findByNameContainsIgnoreCase(String name, Pageable pageable);
    Page<Extra> findByExtraGroup_IdAndExtraGroup_Store_Id( Long extraGroupId, Long storeId, Pageable pageable);
    List<Extra> findAllByIdInAndExtraGroup_Store_Id(List<Long> ids, Long storeId);
}
