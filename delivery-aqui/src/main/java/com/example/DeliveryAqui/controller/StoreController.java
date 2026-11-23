package com.example.DeliveryAqui.controller;

import com.example.DeliveryAqui.dtos.store.StorePostRequest;
import com.example.DeliveryAqui.dtos.store.StorePutRequest;
import com.example.DeliveryAqui.dtos.store.StoreResponse;
import com.example.DeliveryAqui.dtos.store.StoreSummaryResponse;
import com.example.DeliveryAqui.mapper.StoreMapper;
import com.example.DeliveryAqui.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final StoreMapper storeMapper;

    @GetMapping
    public ResponseEntity<Page<StoreSummaryResponse>> findAll (Pageable pageable){
        return new ResponseEntity<>(storeService.findAll(pageable), HttpStatus.OK);
    }
    @GetMapping("/id")
    public ResponseEntity<StoreResponse> findById (@RequestParam Long id){
        return new ResponseEntity<>(storeService.findById(id), HttpStatus.OK);
    }
    @GetMapping("/name")
    public ResponseEntity<Page<StoreSummaryResponse>> findByName(@RequestParam String name, Pageable pageable){
        return new ResponseEntity<>(storeService.findByName(name, pageable), HttpStatus.OK);
    }
    @GetMapping("/cnpj")
    public ResponseEntity<StoreResponse> findByCnpj(@RequestParam String cnpj){
        return new ResponseEntity<>(storeService.findByCnpj(cnpj), HttpStatus.OK);
    }
    @GetMapping("/phoneNumber")
    public ResponseEntity<StoreResponse> findByPhoneNumber(@RequestParam String phoneNumber){
        return new ResponseEntity<>(storeService.findByPhoneNumber(phoneNumber), HttpStatus.OK);
    }
    @GetMapping("/email")
    public ResponseEntity<StoreResponse> findByEmail(@RequestParam String email){
        return new ResponseEntity<>(storeService.findByEmail(email), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StoreResponse> save(@RequestBody @Valid StorePostRequest storePostRequest){
        return new ResponseEntity<>(storeService.save(storePostRequest), HttpStatus.CREATED);
    }

    @PutMapping("/id")
    public ResponseEntity<Void> update (@RequestParam Long id, @RequestBody @Valid StorePutRequest storePutRequest){
        storeService.update(id, storePutRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
