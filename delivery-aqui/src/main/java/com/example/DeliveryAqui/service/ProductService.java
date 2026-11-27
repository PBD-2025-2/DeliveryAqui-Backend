package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupInlineRequest;
import com.example.DeliveryAqui.dtos.product.*;
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

    public ProductDetailResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id, ErrorType.PRODUCT_NOT_FOUND));

        List<Extra> extras = productExtraService.findExtrasByProduct(product.getId());
        return productMapper.entityToDetailResponse(product, extras);
    }

    public Page<ProductSummaryResponse> findByCategoryId(Long categoryId, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", categoryId, ErrorType.CATEGORY_NOT_FOUND));

        return productRepository
                .findByCategoryId(categoryId, pageable)
                .map(productMapper::entityToSummaryResponse);
    }

    public Page<ProductSummaryResponse> findByStoreId(Long storeId, Pageable pageable) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store", storeId, ErrorType.STORE_NOT_FOUND));

        return productRepository
                .findByStoreId(storeId, pageable)
                .map(productMapper::entityToSummaryResponse);
    }

    public Page<ProductSummaryResponse> findByStoreIdAndCategoryId(Long storeId, Long categoryId, Pageable pageable) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store", storeId, ErrorType.STORE_NOT_FOUND));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", categoryId, ErrorType.CATEGORY_NOT_FOUND));

        return productRepository
                .findByStoreIdAndCategoryId(storeId, categoryId, pageable)
                .map(productMapper::entityToSummaryResponse);
    }

    @Transactional
    public ProductDetailResponse create(ProductPostRequest request) {
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow( () -> new ResourceNotFoundException("Store", request.storeId(), ErrorType.STORE_NOT_FOUND));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow( () -> new ResourceNotFoundException("Category", request.categoryId(), ErrorType.CATEGORY_NOT_FOUND));

        if (!category.getStore().getId().equals(store.getId()))
            throw new BusinessException("Category belongs to another store");

        Product product = productMapper.requestToEntity(request);
        product.setStore(store);
        product.setCategory(category);
        product.setAvailable(true);
        product = productRepository.save(product);

        List<Extra> extras = resolveExtras(request.extraIds(), request.newExtraGroups(), store);
        if (!extras.isEmpty()) {
            productExtraService.saveAll(extras, product);
        }

        return productMapper.entityToDetailResponse(product, extras);
    }

    public ProductResponse update(Long id, ProductPutRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", id, ErrorType.PRODUCT_NOT_FOUND));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow( () -> new ResourceNotFoundException("Category", request.categoryId(), ErrorType.CATEGORY_NOT_FOUND));

        if (!category.getStore().getId().equals(product.getStore().getId()))
            throw new BusinessException("Category belongs to another store");

        productMapper.updateEntity(request, product);
        product.setCategory(category);

        product = productRepository.save(product);
        return productMapper.entityToResponse(product);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product", id, ErrorType.PRODUCT_NOT_FOUND);
        }

        productRepository.deleteById(id);
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
