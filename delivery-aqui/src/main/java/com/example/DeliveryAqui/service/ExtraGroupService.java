package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupPostRequest;
import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupPutRequest;
import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupResponse;
import com.example.DeliveryAqui.enums.ErrorType;
import com.example.DeliveryAqui.exception.ResourceNotFoundException;
import com.example.DeliveryAqui.mapper.ExtraGroupMapper;
import com.example.DeliveryAqui.model.entity.ExtraGroup;
import com.example.DeliveryAqui.repository.ExtraGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExtraGroupService {
    private final ExtraGroupRepository extraGroupRepository;
    private final ExtraGroupMapper extraGroupMapper;

    public Page<ExtraGroupResponse> findAll(Pageable pageable) {
        Page<ExtraGroup> extraGroups = extraGroupRepository.findAll(pageable);
        return extraGroups.map(extraGroupMapper::entityToResponse);
    }

    public ExtraGroupResponse findById(Long id) {
        Optional<ExtraGroup> extraGroup = extraGroupRepository.findById(id);
        return extraGroupMapper.entityToResponse(extraGroup
                .orElseThrow(() -> new ResourceNotFoundException("Extra Group", id, ErrorType.EXTRA_GROUP_NOT_FOUND)));
    }

    public  Page<ExtraGroupResponse> findByName(String name, Pageable pageable) {
        Page<ExtraGroup> extraGroups = extraGroupRepository.findByNameContainsIgnoreCase(name, pageable);
        return extraGroups.map(extraGroupMapper::entityToResponse);
    }

    @Transactional
    public ExtraGroupResponse save(ExtraGroupPostRequest postResquest) {
        ExtraGroup  extraGroup = extraGroupMapper.requestToEntity(postResquest);
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
