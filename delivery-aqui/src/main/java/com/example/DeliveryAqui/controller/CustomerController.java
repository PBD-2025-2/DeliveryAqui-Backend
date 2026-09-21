package com.example.DeliveryAqui.controller;

import com.example.DeliveryAqui.dtos.customer.CustomerDetailResponse;
import com.example.DeliveryAqui.dtos.customer.CustomerPostRequest;
import com.example.DeliveryAqui.dtos.customer.CustomerResponse;
import com.example.DeliveryAqui.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CustomerDetailResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerPostRequest request) {
        CustomerResponse response = customerService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        customerService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
