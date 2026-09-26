package com.example.DeliveryAqui.controller;

import com.example.DeliveryAqui.dtos.address.AddressPostRequest;
import com.example.DeliveryAqui.dtos.address.AddressResponse;
import com.example.DeliveryAqui.dtos.personAddress.PersonAddressPostRequest;
import com.example.DeliveryAqui.dtos.personAddress.PersonAddressPutRequest;
import com.example.DeliveryAqui.dtos.personAddress.PersonAddressResponse;
import com.example.DeliveryAqui.dtos.personAddress.PersonAddressSummaryResponse;
import com.example.DeliveryAqui.service.PersonAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/personAdress")
public class PersonAddressController {
    private final PersonAddressService personAddressService;

    @GetMapping
    public ResponseEntity<List<PersonAddressSummaryResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(personAddressService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<PersonAddressResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(personAddressService.findById(id));
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<List<PersonAddressSummaryResponse>> findByCpf(@PathVariable String cpf) {
        return ResponseEntity.status(HttpStatus.OK).body(personAddressService.findByCpf(cpf));
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
