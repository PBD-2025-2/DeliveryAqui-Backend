package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.StorePostRequestDTO;
import com.example.DeliveryAqui.model.entity.Store;
import com.example.DeliveryAqui.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> getAllStores() {
        List<Store> stores = storeRepository.findAll();
        if (stores.isEmpty()) {
            return null;
        }
        return stores;
    }

    public Store getStoreById(Long id) {
        Optional<Store> oStore = storeRepository.findById(id);
        return oStore.orElse(null);
    }

    public void saveStore(StorePostRequestDTO request) {
        Store store = new Store();
        store.setAddress(request.address());
        store.setCnpj(request.cnpj());
        store.setName(request.name());
        store.setEmail(request.email());
        store.setPhoneNumber(request.phoneNumber());
        storeRepository.save(store);
    }

    public void updateStore(Long id, StorePostRequestDTO request) {
        Store store = this.getStoreById(id);

        if (store != null) {
            store.setAddress(request.address());
            store.setCnpj(request.cnpj());
            store.setName(request.name());
            store.setEmail(request.email());
            store.setPhoneNumber(request.phoneNumber());
            storeRepository.save(store);
        }
    }

    public void delete(Long id) {
        storeRepository.deleteById(id);
    }

}
