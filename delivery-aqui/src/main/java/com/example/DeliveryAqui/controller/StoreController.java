package com.example.DeliveryAqui.controller;

import com.example.DeliveryAqui.dtos.StorePostRequestDTO;
import com.example.DeliveryAqui.dtos.StoreResponseDTO;
import com.example.DeliveryAqui.model.entity.Store;
import com.example.DeliveryAqui.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public ResponseEntity<List<StoreResponseDTO>> getAllStores() {
        List<Store> stores = storeService.getAllStores();
        if (stores == null || stores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<StoreResponseDTO> response = stores.stream()
                .map(StoreResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreResponseDTO> getStoreById(@PathVariable Long id) {
        Store store = storeService.getStoreById(id);
        if (store == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(StoreResponseDTO.fromEntity(store));
    }

    @PostMapping
    public ResponseEntity<Void> saveStore(@RequestBody StorePostRequestDTO request) {
        storeService.saveStore(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStore(@PathVariable Long id, @RequestBody StorePostRequestDTO request) {
        Store existingStore = storeService.getStoreById(id);
        if (existingStore == null) {
            return ResponseEntity.notFound().build();
        }
        storeService.updateStore(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        Store existingStore = storeService.getStoreById(id);
        if (existingStore == null) {
            return ResponseEntity.notFound().build();
        }
        storeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
