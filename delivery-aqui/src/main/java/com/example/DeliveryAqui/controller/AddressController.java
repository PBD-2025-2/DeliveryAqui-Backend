package com.example.DeliveryAqui.controller;

import com.example.DeliveryAqui.dtos.address.AddressPostRequest;
import com.example.DeliveryAqui.dtos.address.AddressPutRequest;
import com.example.DeliveryAqui.dtos.address.AddressResponse;
import com.example.DeliveryAqui.dtos.address.AddressSummaryResponse;
import com.example.DeliveryAqui.dtos.shared.PaginatedResponse;
import com.example.DeliveryAqui.enums.AddressSortField;
import com.example.DeliveryAqui.service.AddressService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/addresses")
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<AddressSummaryResponse>> findAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") AddressSortField sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<AddressSummaryResponse> page = addressService.findAll(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(PaginationUtils.buildPaginatedResponse(page));
    }

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<AddressResponse> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AddressResponse> post(@RequestBody @Valid AddressPostRequest postResquest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.save(postResquest));
    }

    @PutMapping
    public ResponseEntity<AddressResponse> put(@RequestParam Long id, @RequestBody @Valid AddressPutRequest putRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.update(id, putRequest));
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
