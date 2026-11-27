package com.example.DeliveryAqui.controller;

import com.example.DeliveryAqui.dtos.product.*;
import com.example.DeliveryAqui.dtos.shared.PaginatedResponse;
import com.example.DeliveryAqui.enums.AddressSortField;
import com.example.DeliveryAqui.service.ProductService;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<PaginatedResponse<ProductSummaryResponse>> getAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") AddressSortField sortField,
            @RequestParam(defaultValue = "ASC")Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<ProductSummaryResponse> page = productService.findAll(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PaginationUtils.buildPaginatedResponse(page));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProductDetailResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("/category/id/{id}")
    public ResponseEntity<PaginatedResponse<ProductSummaryResponse>> getByCategoryId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") AddressSortField sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction

    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<ProductSummaryResponse> page = productService.findByCategoryId(id, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PaginationUtils.buildPaginatedResponse(page));
    }

    @GetMapping("/store/id/{id}")
    public ResponseEntity<PaginatedResponse<ProductSummaryResponse>> getByStoreId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") AddressSortField sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction

    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<ProductSummaryResponse> page = productService.findByStoreId(id, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PaginationUtils.buildPaginatedResponse(page));
    }

    @GetMapping("/storeId/{storeId}/categoryId/{categoryId}")
    public ResponseEntity<PaginatedResponse<ProductSummaryResponse>> getByStoreIdAndCategoryId(
            @PathVariable Long storeId,
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") AddressSortField sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction

    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<ProductSummaryResponse> page = productService.findByStoreIdAndCategoryId(storeId, categoryId, pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PaginationUtils.buildPaginatedResponse(page));
    }

    @PostMapping
    public ResponseEntity<ProductDetailResponse> create(@Valid @RequestBody ProductPostRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.create(request));
    }

    @PutMapping
    public ResponseEntity<ProductResponse> put(@RequestParam Long id, @RequestBody @Valid ProductPutRequest putRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.update(id, putRequest));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
