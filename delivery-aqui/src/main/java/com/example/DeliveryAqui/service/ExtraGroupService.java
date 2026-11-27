package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.extra.ExtraInlineRequest;
import com.example.DeliveryAqui.dtos.extraGroup.*;
import com.example.DeliveryAqui.enums.ErrorType;
import com.example.DeliveryAqui.exception.ResourceNotFoundException;
import com.example.DeliveryAqui.mapper.ExtraGroupMapper;
import com.example.DeliveryAqui.mapper.ExtraMapper;
import com.example.DeliveryAqui.model.entity.Extra;
import com.example.DeliveryAqui.model.entity.ExtraGroup;
import com.example.DeliveryAqui.model.entity.Store;
import com.example.DeliveryAqui.repository.ExtraGroupRepository;
import com.example.DeliveryAqui.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExtraGroupService {
    private final ExtraGroupRepository extraGroupRepository;
    private final ExtraGroupMapper extraGroupMapper;
    private final StoreRepository storeRepository;
    private final ExtraMapper extraMapper;
    private final ExtraService extraService;

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

    public  Page<ExtraGroupDetailResponse> findByStore(Long storeId, Pageable pageable) {
        Page<ExtraGroup> extraGroups = extraGroupRepository.findByStore_Id(storeId, pageable);
        return extraGroups.map(extraGroupMapper::entityToDetailResponse);
    }

    @Transactional
    public ExtraGroupResponse save(ExtraGroupPostRequest postRequest) {
        Store store = storeRepository.findById(postRequest.storeId())
                .orElseThrow(() -> new ResourceNotFoundException("Store", postRequest.storeId(), ErrorType.STORE_NOT_FOUND));

        ExtraGroup  extraGroup = extraGroupMapper.requestToEntity(postRequest);
        extraGroup.setStore(store);

        extraGroup = extraGroupRepository.save(extraGroup);

        if (postRequest.newExtras() != null && !postRequest.newExtras().isEmpty()) {
            List<Extra> extras = extraService.saveAll(postRequest.newExtras(), extraGroup);
            extraGroup.setExtras(extras);
        }

        return extraGroupMapper.entityToResponse(extraGroup);
    }

    @Transactional
    public List<ExtraGroupDetailResponse> saveAll(ExtraGroupBatchPostRequest batchPostRequest) {
        Store store = storeRepository.findById(batchPostRequest.storeId())
                .orElseThrow(() -> new ResourceNotFoundException("Store", batchPostRequest.storeId(), ErrorType.STORE_NOT_FOUND));

        List<ExtraGroup> extraGroups = toEntities(batchPostRequest.extraGroups(), store);
        extraGroups = extraGroupRepository.saveAll(extraGroups);
        return extraGroupMapper.entityToDetailResponse(extraGroups);
    }

    @Transactional
    public List<ExtraGroup> saveAll(List<ExtraGroupInlineRequest> requests, Store store) {
        List<ExtraGroup> extraGroups = toEntities(requests, store);
        return extraGroupRepository.saveAll(extraGroups);
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

    private void addExtras(ExtraGroup extraGroup, List<ExtraInlineRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            return;
        }

        List<Extra> extras = extraMapper.requestToEntity(requests);
        extras.forEach(extra -> extra.setExtraGroup(extraGroup));
        extraGroup.getExtras().addAll(extras);
    }

    private List<ExtraGroup> toEntities(List<ExtraGroupInlineRequest> requests, Store store) {
        return requests.stream()
                .map(request -> {
                    ExtraGroup extraGroup = extraGroupMapper.requestToEntity(request);
                    extraGroup.setStore(store);
                    addExtras(extraGroup, request.newExtras());
                    return extraGroup;
                }).toList();
    }
}
