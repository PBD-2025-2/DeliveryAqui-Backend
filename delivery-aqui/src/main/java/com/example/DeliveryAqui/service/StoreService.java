package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.address.AddressResponse;
import com.example.DeliveryAqui.dtos.store.StorePostRequest;
import com.example.DeliveryAqui.dtos.store.StorePutRequest;
import com.example.DeliveryAqui.dtos.store.StoreResponse;
import com.example.DeliveryAqui.dtos.store.StoreSummaryResponse;
import com.example.DeliveryAqui.exception.address.AddressNotFoundException;
import com.example.DeliveryAqui.exception.store.StoreInUseException;
import com.example.DeliveryAqui.exception.store.StoreNotFoundException;
import com.example.DeliveryAqui.mapper.AddressMapper;
import com.example.DeliveryAqui.mapper.StoreMapper;
import com.example.DeliveryAqui.model.entity.Address;
import com.example.DeliveryAqui.model.entity.Store;
import com.example.DeliveryAqui.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    public Page<StoreSummaryResponse> findAll (Pageable pageable){
        Page<Store> stores = storeRepository.findAll(pageable);
        return stores.map(storeMapper :: entityToStoreSummaryResponse);
    }

    public StoreResponse findById(Long id){
        Optional<Store> store = storeRepository.findById(id);
        return storeMapper.entityToStoreResponse(store
                .orElseThrow(() -> new StoreNotFoundException(id)));
    }

    public Page<StoreSummaryResponse> findByName(String name, Pageable pageable){
        Page<Store> stores = storeRepository.findByNameContainsIgnoreCase(name, pageable);
        return stores.map(storeMapper :: entityToStoreSummaryResponse);
    }

    public StoreResponse findByCnpj(String cnpj){
        Optional<Store> store = storeRepository.findByCnpj(cnpj);
        return storeMapper.entityToStoreResponse(store
                .orElseThrow(() -> new StoreNotFoundException(cnpj)));
    }

    public StoreResponse findByPhoneNumber(String phoneNumber){
        Optional<Store> store = storeRepository.findByPhoneNumber(phoneNumber);
        return storeMapper.entityToStoreResponse(store
                .orElseThrow(() -> new StoreNotFoundException(phoneNumber)));
    }

    public StoreResponse findByEmail(String email){
        Optional<Store> store = storeRepository.findByEmail(email);
        return storeMapper.entityToStoreResponse(store
                .orElseThrow(() -> new StoreNotFoundException(email)));
    }

    @Transactional
    public StoreResponse save (StorePostRequest storePostRequest){
        AddressResponse addressResponse = addressService.findById(storePostRequest.address_id());
        Address address = addressMapper.responseToEntity(addressResponse);
        Store store = storeMapper.responseToEntity(storePostRequest);
        store.setCreatedAt(Instant.now());
        store.setAddress(address);
        Store savedStore = storeRepository.save(store);
        return storeMapper.entityToStoreResponse(savedStore);
    }

    @Transactional
    public StoreResponse update(Long id, StorePutRequest storePutRequest){
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new AddressNotFoundException(id));
        storeMapper.updateEntity(storePutRequest, store);
        store.setUpdatedAt(Instant.now());
        storeRepository.save(store);
        return storeMapper.entityToStoreResponse(store);
    }

    @Transactional
    public void delete(Long id){
        Store store = storeRepository.findById(id).orElseThrow(
                () -> new StoreNotFoundException(id) {
                });
        try{
            storeRepository.delete(store);
            storeRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new StoreInUseException(id);
        }
    }

}
