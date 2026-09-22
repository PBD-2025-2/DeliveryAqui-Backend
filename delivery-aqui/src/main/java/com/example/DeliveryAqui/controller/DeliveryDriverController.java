package com.example.DeliveryAqui.controller;

import com.example.DeliveryAqui.dtos.deliveryDriver.DeliveryDriverDetailResponse;
import com.example.DeliveryAqui.dtos.deliveryDriver.DeliveryDriverPostRequest;
import com.example.DeliveryAqui.dtos.deliveryDriver.DeliveryDriverResponse;
import com.example.DeliveryAqui.service.DeliveryDriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/deliveryDrivers")
public class DeliveryDriverController {

    private final DeliveryDriverService deliveryDriverService;

    @GetMapping
    public ResponseEntity<List<DeliveryDriverResponse>> getAll() {
        return ResponseEntity.ok(deliveryDriverService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<DeliveryDriverDetailResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryDriverService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DeliveryDriverResponse> create(@Valid @RequestBody DeliveryDriverPostRequest request) {
        DeliveryDriverResponse response = deliveryDriverService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/work-mode/id/{id}")
    public ResponseEntity<DeliveryDriverResponse> changeWorkMode(@PathVariable Long id, boolean mode) {
        DeliveryDriverResponse response = deliveryDriverService.changeWorkMode(id, mode);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        deliveryDriverService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
