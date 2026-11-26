package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.model.entity.Extra;
import com.example.DeliveryAqui.model.entity.Product;
import com.example.DeliveryAqui.model.entity.ProductExtra;
import com.example.DeliveryAqui.repository.ProductExtraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductExtraService {
    private final ProductExtraRepository productExtraRepository;

    public List<Extra> findExtrasByProduct(Long productId) {
        return productExtraRepository.findAllByProduct_Id(productId).stream()
                .map(ProductExtra::getExtra)
                .toList();
    }

    public void saveAll(List<Extra> extras, Product product) {
        List<ProductExtra> productExtras = extras.stream()
            .map(extra -> {
                ProductExtra productExtra = new ProductExtra();
                productExtra.setProduct(product);
                productExtra.setExtra(extra);
                productExtra.setIsAvailable(true);
                return productExtra;
            }).toList();

        productExtraRepository.saveAll(productExtras);
    }
}
