package com.example.DeliveryAqui.controller;

import com.example.DeliveryAqui.dtos.category.CategoryPostRequest;
import com.example.DeliveryAqui.dtos.category.CategoryResponse;
import com.example.DeliveryAqui.dtos.category.CategorySummaryResponse;
import com.example.DeliveryAqui.dtos.product.ProductSummaryResponse;
import com.example.DeliveryAqui.dtos.shared.PaginatedResponse;
import com.example.DeliveryAqui.enums.AddressSortField;
import com.example.DeliveryAqui.service.CategoryService;
import com.example.DeliveryAqui.util.PaginationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<CategorySummaryResponse>> getAll() {

        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryPostRequest request) {
        CategoryResponse response = categoryService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/store/id/{id}")
    public ResponseEntity<PaginatedResponse<CategorySummaryResponse>> getByStoreId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") AddressSortField sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction

    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<CategorySummaryResponse> page = categoryService.findByStoreId(id, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PaginationUtils.buildPaginatedResponse(page));
    }
}
