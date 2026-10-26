package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.product.ProductPostRequest;
import com.example.DeliveryAqui.dtos.product.ProductResponse;
import com.example.DeliveryAqui.dtos.product.ProductSummaryResponse;
import com.example.DeliveryAqui.mapper.ProductMapper;
import com.example.DeliveryAqui.model.entity.Category;
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


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;
    private final ProductMapper productMapper;

    public Page<ProductSummaryResponse> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(productMapper::entityToSummaryResponse);
    }

    @Transactional
    public ProductResponse create(ProductPostRequest request) {
        Store store = storeRepository.findById(request.storeId())
            .orElseThrow( () -> new RuntimeException("Store not found"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow( () -> new RuntimeException("Category not found"));

        if (!category.getStore().getId().equals(store.getId()))
            throw new RuntimeException("Category belongs to another store");

        Product product = productMapper.requestToEntity(request);
        product = productRepository.save(product);
        return productMapper.entityToResponse(product);
    }

    public ProductResponse findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return productMapper.entityToResponse(product);
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


}
