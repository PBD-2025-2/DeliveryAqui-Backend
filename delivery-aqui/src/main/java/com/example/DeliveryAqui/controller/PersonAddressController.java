package com.example.DeliveryAqui.controller;

import com.example.DeliveryAqui.dtos.personAddress.PersonAddressPostRequest;
import com.example.DeliveryAqui.dtos.personAddress.PersonAddressPutRequest;
import com.example.DeliveryAqui.dtos.personAddress.PersonAddressResponse;
import com.example.DeliveryAqui.dtos.personAddress.PersonAddressSummaryResponse;
import com.example.DeliveryAqui.dtos.shared.PaginatedResponse;
import com.example.DeliveryAqui.enums.PersonAddressSortField;
import com.example.DeliveryAqui.service.PersonAddressService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/personAdress")
public class PersonAddressController {
    private final PersonAddressService personAddressService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<PersonAddressSummaryResponse>> findAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") PersonAddressSortField sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<PersonAddressSummaryResponse> page = personAddressService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(PaginationUtils.buildPaginatedResponse(page));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PersonAddressResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(personAddressService.findById(id));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<PaginatedResponse<PersonAddressSummaryResponse>> findByCpf(
            @PathVariable String cpf,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ID") PersonAddressSortField sortField,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sortField.getField()));
        Page<PersonAddressSummaryResponse> page = personAddressService.findByCpf(cpf, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(PaginationUtils.buildPaginatedResponse(page));
    }

    @GetMapping("/person/{id}/favorite")
    public ResponseEntity<PersonAddressResponse> findFavoriteAddress(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(personAddressService.findFavoriteAddress(id));
    }

    @PostMapping
    public ResponseEntity<PersonAddressResponse> post(@RequestBody @Valid PersonAddressPostRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personAddressService.save(request));
    }

    @PutMapping("id/{id}")
    public ResponseEntity<PersonAddressResponse> post(@PathVariable  Long id, @RequestBody @Valid PersonAddressPutRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(personAddressService.update(id, request));
    }

    @PatchMapping("{id}/favorite")
    public ResponseEntity<PersonAddressResponse> switchFavorite(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(personAddressService.switchFavorite(id));
    }
}
