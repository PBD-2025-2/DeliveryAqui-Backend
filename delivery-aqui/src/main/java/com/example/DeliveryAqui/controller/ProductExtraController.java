package com.example.DeliveryAqui.controller;

import com.example.DeliveryAqui.dtos.productExtra.ProductExtraDeleteRequest;
import com.example.DeliveryAqui.dtos.productExtra.ProductExtraPutRequest;
import com.example.DeliveryAqui.dtos.productExtra.ProductExtraResponse;
import com.example.DeliveryAqui.service.ProductExtraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/product-extras")
public class ProductExtraController {
    private final ProductExtraService productExtraService;

    @PutMapping(path = "/products/{productId}/extras")
    public ResponseEntity<List<ProductExtraResponse>> updateAll(@PathVariable Long productId, @Valid @RequestBody ProductExtraPutRequest putRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(productExtraService.updateAll(productId, putRequest));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        productExtraService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/products/{productId}")
    public ResponseEntity<Void> deleteAllByProductId(@PathVariable Long productId) {
        productExtraService.deleteAllByProductId(productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/products/{productId}/extras")
    public ResponseEntity<Void> deleteAllByExtraIds(@PathVariable Long productId, @Valid @RequestBody ProductExtraDeleteRequest deleteRequest) {
        productExtraService.deleteAllByExtraIds(productId, deleteRequest);
        return ResponseEntity.noContent().build();
    }

}
