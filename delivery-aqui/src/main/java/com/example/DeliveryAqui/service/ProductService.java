package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupInlineRequest;
import com.example.DeliveryAqui.dtos.product.ProductPostRequest;
import com.example.DeliveryAqui.dtos.product.ProductResponse;
import com.example.DeliveryAqui.dtos.product.ProductSummaryResponse;
import com.example.DeliveryAqui.enums.ErrorType;
import com.example.DeliveryAqui.exception.BusinessException;
import com.example.DeliveryAqui.exception.ResourceNotFoundException;
import com.example.DeliveryAqui.mapper.ProductMapper;
import com.example.DeliveryAqui.model.entity.Category;
import com.example.DeliveryAqui.model.entity.Extra;
import com.example.DeliveryAqui.model.entity.Product;
import com.example.DeliveryAqui.model.entity.Store;
import com.example.DeliveryAqui.repository.CategoryRepository;
import com.example.DeliveryAqui.repository.ProductRepository;
import com.example.DeliveryAqui.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;
    private final ExtraService extraService;
    private final ExtraGroupService extraGroupService;
    private final ProductExtraService productExtraService;
    private final ProductMapper productMapper;

    public Page<ProductSummaryResponse> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::entityToSummaryResponse);
    }

    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<Extra> extras = productExtraService.findExtrasByProduct(product.getId());
        return productMapper.entityToResponse(product, extras);
    }

    public Page<ProductSummaryResponse> findByCategoryId(Long categoryId, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Page<ProductSummaryResponse> products = productRepository
                .findByCategoryId(categoryId, pageable)
                .map(productMapper::entityToSummaryResponse);

        return products;
    }

    public Page<ProductSummaryResponse> findByStoreId(Long storeId, Pageable pageable) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        Page<ProductSummaryResponse> products = productRepository
                .findByStoreId(storeId, pageable)
                .map(productMapper::entityToSummaryResponse);

        return products;
    }

    public Page<ProductSummaryResponse> findByStoreIdAndCategoryId(Long storeId, Long categoryId, Pageable pageable) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Page<ProductSummaryResponse> products = productRepository
                .findByStoreIdAndCategoryId(storeId, categoryId, pageable)
                .map(productMapper::entityToSummaryResponse);

        return products;
    }

    @Transactional
    public ProductResponse create(ProductPostRequest request) {
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow( () -> new ResourceNotFoundException("Store", request.storeId(), ErrorType.STORE_NOT_FOUND));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow( () -> new ResourceNotFoundException("Category", request.categoryId(), ErrorType.CATEGORY_NOT_FOUND));

        if (!category.getStore().getId().equals(store.getId()))
            throw new BusinessException("Category belongs to another store");

        Product product = productMapper.requestToEntity(request);
        product.setStore(store);
        product.setCategory(category);
        product = productRepository.save(product);

        List<Extra> extras = resolveExtras(request.extraIds(), request.newExtraGroups(), store);
        if (!extras.isEmpty()) {
            productExtraService.saveAll(extras, product);
        }

        return productMapper.entityToResponse(product, extras);
    }

    private List<Extra> resolveExtras(List<Long> extraIds, List<ExtraGroupInlineRequest> newExtraGroups, Store store) {
        List<Extra> extras = new ArrayList<>(findExistingExtras(extraIds, store));
        extras.addAll(saveNewExtras(newExtraGroups, store));
        return extras;
    }

    private List<Extra> findExistingExtras(List<Long> extraIds, Store store) {
        if (extraIds == null || extraIds.isEmpty()) {
            return List.of();
        }
        return extraService.findAllByIdsAndStoreId(extraIds, store.getId());
    }

    private List<Extra> saveNewExtras(List<ExtraGroupInlineRequest> newExtraGroups, Store store) {
        if (newExtraGroups == null || newExtraGroups.isEmpty()) {
            return List.of();
        }

        return extraGroupService.saveAll(newExtraGroups, store).stream()
                .flatMap(extraGroup -> extraGroup.getExtras().stream())
                .toList();
    }
}
