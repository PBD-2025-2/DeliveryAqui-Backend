package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupPostRequest;
import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupPutRequest;
import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupResponse;
import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupSummaryResponse;
import com.example.DeliveryAqui.enums.ErrorType;
import com.example.DeliveryAqui.exception.ResourceNotFoundException;
import com.example.DeliveryAqui.mapper.ExtraGroupMapper;
import com.example.DeliveryAqui.model.entity.ExtraGroup;
import com.example.DeliveryAqui.model.entity.Store;
import com.example.DeliveryAqui.repository.ExtraGroupRepository;
import com.example.DeliveryAqui.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExtraGroupService {
    private final ExtraGroupRepository extraGroupRepository;
    private final ExtraGroupMapper extraGroupMapper;
    private final StoreRepository storeRepository;

    public Page<ExtraGroupSummaryResponse> findAll(Pageable pageable) {
        Page<ExtraGroup> extraGroups = extraGroupRepository.findAll(pageable);
        return extraGroups.map(extraGroupMapper::entityToSummaryResponse);
    }

    public ExtraGroupResponse findById(Long id) {
        ExtraGroup extraGroup = extraGroupRepository.findWithExtrasById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Extra Group", id, ErrorType.EXTRA_GROUP_NOT_FOUND));
        return extraGroupMapper.entityToResponse(extraGroup);
    }

    public  Page<ExtraGroupSummaryResponse> findByName(String name, Pageable pageable) {
        Page<ExtraGroup> extraGroups = extraGroupRepository.findByNameContainsIgnoreCase(name, pageable);
        return extraGroups.map(extraGroupMapper::entityToSummaryResponse);
    }

    public  Page<ExtraGroupSummaryResponse> findByStore(Long storeId, Pageable pageable) {
        Page<ExtraGroup> extraGroups = extraGroupRepository.findByStore_Id(storeId, pageable);
        return extraGroups.map(extraGroupMapper::entityToSummaryResponse);
    }

    @Transactional
    public ExtraGroupResponse save(ExtraGroupPostRequest postRequest) {
        Store store = storeRepository.findById(postRequest.storeId())
                .orElseThrow(() -> new ResourceNotFoundException("Store", postRequest.storeId(), ErrorType.STORE_NOT_FOUND));

        ExtraGroup  extraGroup = extraGroupMapper.requestToEntity(postRequest);
        extraGroup.setStore(store);

        extraGroupRepository.save(extraGroup);
        return extraGroupMapper.entityToResponse(extraGroup);
    }

    @Transactional
    public ExtraGroupResponse update(Long id, ExtraGroupPutRequest putRequest) {
        ExtraGroup extraGroup = extraGroupRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Extra Group", id, ErrorType.EXTRA_GROUP_NOT_FOUND));

        extraGroupMapper.updateEntity(putRequest, extraGroup);
        extraGroupRepository.save(extraGroup);
        return extraGroupMapper.entityToResponse(extraGroup);
    }

    @Transactional
    public void delete(Long id) {
        ExtraGroup extraGroup = extraGroupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Extra Group", id, ErrorType.EXTRA_GROUP_NOT_FOUND));

        extraGroupRepository.delete(extraGroup);
    }
}
