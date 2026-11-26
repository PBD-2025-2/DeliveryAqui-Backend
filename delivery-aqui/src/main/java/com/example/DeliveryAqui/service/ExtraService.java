package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.extra.*;
import com.example.DeliveryAqui.enums.ErrorType;
import com.example.DeliveryAqui.exception.ResourceNotFoundException;
import com.example.DeliveryAqui.mapper.ExtraMapper;
import com.example.DeliveryAqui.model.entity.Extra;
import com.example.DeliveryAqui.model.entity.ExtraGroup;
import com.example.DeliveryAqui.repository.ExtraGroupRepository;
import com.example.DeliveryAqui.repository.ExtraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExtraService {
    private final ExtraRepository extraRepository;
    private final ExtraGroupRepository extraGroupRepository;
    private final ExtraMapper extraMapper;

    public Page<ExtraSummaryResponse> findAll(Pageable pageable) {
        Page<Extra> extras = extraRepository.findAll(pageable);
        return extras.map(extraMapper::entityToSummaryResponse);
    }

    @Transactional(readOnly = true)
    public List<Extra> findAllByIdsAndStoreId(List<Long> ids, Long storeId) {
        Set<Long> distinctIds = new HashSet<>(ids);
        List<Extra> extras = extraRepository.findAllByIdInAndExtraGroup_Store_Id(ids, storeId);

        if (extras.size() != distinctIds.size()) {
            Set<Long> foundIds = extras.stream()
                    .map(Extra::getId)
                    .collect(Collectors.toSet());

            List<Long> notFoundIds = distinctIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .distinct()
                    .toList();

            throw new ResourceNotFoundException("Extras", notFoundIds, ErrorType.EXTRA_NOT_FOUND);
        }
        return extras;
    }

    public ExtraResponse findById(Long id) {
        Optional<Extra> extra = extraRepository.findById(id);
        return extraMapper.entityToResponse(extra
                .orElseThrow(() -> new ResourceNotFoundException("Extra", id, ErrorType.EXTRA_NOT_FOUND)));
    }

    public  Page<ExtraSummaryResponse> findByName(String name, Pageable pageable) {
        Page<Extra> extras = extraRepository.findByNameContainsIgnoreCase(name, pageable);
        return extras.map(extraMapper::entityToSummaryResponse);
    }

    public Page<ExtraSummaryResponse> findByExtraGroup(Long extraGroupId, Long storeId, Pageable pageable) {
        if (!extraGroupRepository.existsById(extraGroupId)) {
            throw new ResourceNotFoundException("Extra Group", extraGroupId, ErrorType.EXTRA_GROUP_NOT_FOUND);
        }

        return extraRepository
                .findByExtraGroup_IdAndExtraGroup_Store_Id(extraGroupId, storeId, pageable)
                .map(extraMapper::entityToSummaryResponse);
    }

    @Transactional
    public ExtraResponse save(ExtraPostRequest postRequest) {
        ExtraGroup extraGroup = extraGroupRepository.findById(postRequest.extraGroupId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Extra Group", postRequest.extraGroupId(), ErrorType.EXTRA_GROUP_NOT_FOUND));

        Extra extra = extraMapper.requestToEntity(postRequest);
        extra.setExtraGroup(extraGroup);

        extraRepository.save(extra);
        return extraMapper.entityToResponse(extra);
    }

    @Transactional
    public List<ExtraSummaryResponse> saveAll(ExtraBatchPostRequest batchPostRequest) {
        ExtraGroup extraGroup = extraGroupRepository.findById(batchPostRequest.extraGroupId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Extra Group", batchPostRequest.extraGroupId(), ErrorType.EXTRA_GROUP_NOT_FOUND));

        List<Extra> extras = extraMapper.requestToEntity(batchPostRequest.extras());
        extras.forEach(extra -> extra.setExtraGroup(extraGroup));

        extras = extraRepository.saveAll(extras);
        return extraMapper.entityToSummaryResponse(extras);
    }

    @Transactional
    public List<Extra> saveAll(List<ExtraInlineRequest> postRequests, ExtraGroup extraGroup) {
        List<Extra> extras = extraMapper.requestToEntity(postRequests);
        extras.forEach(extra -> extra.setExtraGroup(extraGroup));;
        return extraRepository.saveAll(extras);
    }

    @Transactional
    public ExtraResponse update(Long id, ExtraPutRequest putRequest) {
        Extra extra = extraRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Extra", id, ErrorType.EXTRA_NOT_FOUND));

        extraMapper.updateEntity(putRequest, extra);
        extraRepository.save(extra);
        return extraMapper.entityToResponse(extra);
    }

    @Transactional
    public void delete(Long id) {
        Extra extra = extraRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Extra", id, ErrorType.EXTRA_NOT_FOUND));

        extraRepository.delete(extra);
    }

}
