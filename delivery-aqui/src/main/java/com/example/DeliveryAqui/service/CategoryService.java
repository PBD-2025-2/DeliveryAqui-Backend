package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.category.CategoryPostRequest;
import com.example.DeliveryAqui.dtos.category.CategoryResponse;
import com.example.DeliveryAqui.dtos.category.CategorySummaryResponse;
import com.example.DeliveryAqui.mapper.CategoryMapper;
import com.example.DeliveryAqui.mapper.ProductMapper;
import com.example.DeliveryAqui.model.entity.Category;
import com.example.DeliveryAqui.model.entity.Store;
import com.example.DeliveryAqui.repository.CategoryRepository;
import com.example.DeliveryAqui.repository.ProductRepository;
import com.example.DeliveryAqui.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    public List<CategorySummaryResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();

        return categoryMapper.entityListToResponse(categories);
    }

    public Page<CategorySummaryResponse> findByStoreId(Long storeId, Pageable pageable) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("Store not found"));

        Page<CategorySummaryResponse> categories = categoryRepository
                .findByStoreId(storeId, pageable)
                .map(categoryMapper::entityToSummaryResponse);

        return categories;
    }

    @Transactional
    public CategoryResponse create(CategoryPostRequest request) {
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow( () -> new RuntimeException("Store not found"));

        if (categoryRepository.existsByStoreIdAndName(store.getId(), request.name()))
            throw new RuntimeException("Category already exists in this store");

        Category parent = null;
        if (request.parentId() != null) {
            parent = categoryRepository.findById(request.parentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));

            if (!parent.getStore().getId().equals(store.getId()))
                throw new RuntimeException("Parent category belongs to another store");

        }

        Category category = categoryMapper.requestToEntity(request);
        category.setParent(parent);
        category.setStore(store);
        category.setPath(getPath(category));
        categoryRepository.save(category);

        return categoryMapper.entityToResponse(category);
    }


    private String getPath(Category category) {
        if (category.getParent() == null)
            return category.getName();

        return category.getParent().getPath() + " > " + category.getName();
    }
}
