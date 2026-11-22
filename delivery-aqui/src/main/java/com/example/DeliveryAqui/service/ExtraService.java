package com.example.DeliveryAqui.service;

import com.example.DeliveryAqui.dtos.extra.ExtraPostRequest;
import com.example.DeliveryAqui.dtos.extra.ExtraPutRequest;
import com.example.DeliveryAqui.dtos.extra.ExtraResponse;
import com.example.DeliveryAqui.dtos.extra.ExtraSummaryResponse;
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

import java.util.Optional;

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
