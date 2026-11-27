package com.example.DeliveryAqui.repository;

import com.example.DeliveryAqui.model.entity.ProductExtra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductExtraRepository extends JpaRepository<ProductExtra, Long> {
    List<ProductExtra> findAllByProduct_Id(Long productId);
    Optional<ProductExtra> findByProduct_IdAndExtra_Id(Long productId, Long extraId);
    void deleteAllByProduct_Id(Long productId);
    void deleteAllByProduct_IdAndExtra_IdIn(Long product_id, List<Long> extraIds);
    long countByProduct_IdAndExtra_IdIn(Long productId, List<Long> extraIds);
}
