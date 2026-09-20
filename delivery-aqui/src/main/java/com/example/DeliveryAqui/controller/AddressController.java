package com.example.DeliveryAqui.controller;

import com.example.DeliveryAqui.dto.address.AddressPostResquest;
import com.example.DeliveryAqui.dto.address.AddressPutRequest;
import com.example.DeliveryAqui.dto.address.AddressResponse;
import com.example.DeliveryAqui.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findAll());
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<AddressResponse> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AddressResponse> post(@RequestBody @Valid AddressPostResquest postResquest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.save(postResquest));
    }

    @PutMapping
    public ResponseEntity<AddressResponse> put(@RequestParam Long id, @RequestBody @Valid AddressPutRequest putRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.update(id, putRequest));
    }

    @DeleteMapping
    public ResponseEntity<Void> put(@RequestParam Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
