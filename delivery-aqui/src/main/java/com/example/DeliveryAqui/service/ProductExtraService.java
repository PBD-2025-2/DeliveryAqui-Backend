package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.productExtra.ProductExtraDeleteRequest;
import com.example.DeliveryAqui.dtos.productExtra.ProductExtraPatchRequest;
import com.example.DeliveryAqui.dtos.productExtra.ProductExtraPutRequest;
import com.example.DeliveryAqui.enums.ErrorType;
import com.example.DeliveryAqui.exception.BusinessException;
import com.example.DeliveryAqui.exception.ResourceNotFoundException;
import com.example.DeliveryAqui.mapper.ProductExtraMapper;
import com.example.DeliveryAqui.model.entity.Extra;
import com.example.DeliveryAqui.model.entity.Product;
import com.example.DeliveryAqui.model.entity.ProductExtra;
import com.example.DeliveryAqui.dtos.productExtra.ProductExtraResponse;
import com.example.DeliveryAqui.repository.ProductExtraRepository;
import com.example.DeliveryAqui.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductExtraService {
    private final ProductExtraRepository productExtraRepository;
    private final ProductRepository productRepository;
    private final ProductExtraMapper productExtraMapper;
    private final ExtraService extraService;

    public List<Extra> findExtrasByProduct(Long productId) {
        return productExtraRepository.findAllByProduct_Id(productId).stream()
                .map(ProductExtra::getExtra)
                .toList();
    }

    @Transactional
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

    @Transactional
    public ProductExtraResponse update(Long productId, Long extraId, ProductExtraPatchRequest patchRequest) {
        ProductExtra productExtra = productExtraRepository.findByProduct_IdAndExtra_Id(productId, extraId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Extra", extraId, ErrorType.PRODUCT_EXTRA_NOT_FOUND));

        productExtraMapper.updateEntity(patchRequest, productExtra);
        productExtra = productExtraRepository.save(productExtra);
        return productExtraMapper.entityToResponse(productExtra);
    }

    @Transactional
    public List<ProductExtraResponse> updateAll(Long productId, ProductExtraPutRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", productId, ErrorType.PRODUCT_NOT_FOUND));

        List<Extra> extras = extraService.findAllByIdsAndStoreId(request.extraIds(), product.getStore().getId());
        List<ProductExtra> currentProductExtras = productExtraRepository.findAllByProduct_Id(product.getId());

        Set<Long> newExtraIds = getExtraIds(extras);
        Set<Long> currentExtraIds = getExtraIdsFromProduct(currentProductExtras);

        removeUnselectedProductExtras(currentProductExtras, newExtraIds);
        addNewProductExtras(product, extras, currentExtraIds);

        List<ProductExtra> updatedProductExtras = productExtraRepository.findAllByProduct_Id(productId);
        return productExtraMapper.entityToResponse(updatedProductExtras);
    }

    @Transactional
    public void delete(Long productExtraId) {
        if (!productExtraRepository.existsById(productExtraId)) {
            throw new ResourceNotFoundException("Product Extra", productExtraId, ErrorType.PRODUCT_EXTRA_NOT_FOUND);
        }

        productExtraRepository.deleteById(productExtraId);
    }

    @Transactional
    public void deleteAllByProductId(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product", productId, ErrorType.PRODUCT_NOT_FOUND);
        }

        productExtraRepository.deleteAllByProduct_Id(productId);
    }

    @Transactional
    public void deleteAllByExtraIds(Long productId, ProductExtraDeleteRequest deleteRequest) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException("Product", productId, ErrorType.PRODUCT_NOT_FOUND);
        }

        Set<Long> distinctIds = new HashSet<>(deleteRequest.extraIds());
        long count = productExtraRepository.countByProduct_IdAndExtra_IdIn(productId, distinctIds.stream().toList());

        if (count != distinctIds.size()) {
            throw new BusinessException("One or more extras are not associate with this product.");
        }

        productExtraRepository.deleteAllByProduct_IdAndExtra_IdIn(productId, distinctIds.stream().toList());
    }

    private Set<Long> getExtraIds(List<Extra> extras) {
        return extras.stream()
                .map(Extra::getId)
                .collect(Collectors.toSet());
    }

    private Set<Long> getExtraIdsFromProduct(List<ProductExtra> productExtras) {
        return productExtras.stream()
                .map(productExtra -> productExtra.getExtra().getId())
                .collect(Collectors.toSet());
    }

    private void addNewProductExtras(Product product, List<Extra> newExtras, Set<Long> currentExtraIds) {
        List<ProductExtra> newProductExtras = newExtras.stream()
                .filter(extra -> !currentExtraIds.contains(extra.getId()))
                .map(extra -> createProductExtra(product, extra))
                .toList();

        productExtraRepository.saveAll(newProductExtras);
    }

    private void removeUnselectedProductExtras(List<ProductExtra> currentProductExtras, Set<Long> newExtrasIds) {
        List<ProductExtra> toRemove = currentProductExtras.stream()
                .filter(productExtra -> !newExtrasIds.contains(productExtra.getExtra().getId()))
                .toList();

        productExtraRepository.deleteAll(toRemove);
    }

    private ProductExtra createProductExtra(Product product, Extra extra) {
        ProductExtra productExtra = new ProductExtra();
        productExtra.setProduct(product);
        productExtra.setExtra(extra);
        productExtra.setIsAvailable(true);
        return productExtra;
    }
}
