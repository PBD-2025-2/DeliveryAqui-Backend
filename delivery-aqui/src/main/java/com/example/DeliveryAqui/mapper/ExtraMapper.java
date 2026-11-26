package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dtos.extra.*;
import com.example.DeliveryAqui.model.entity.Extra;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ExtraMapper {
    ExtraResponse entityToResponse(Extra extra);

    @Mapping(source = "extraGroup.id", target = "extraGroupId")
    ExtraSummaryResponse entityToSummaryResponse(Extra extra);
    List<ExtraSummaryResponse> entityToSummaryResponse(List<Extra> extras);

    @Mapping(source = "extraGroupId", target = "extraGroup.id")
    Extra requestToEntity(ExtraPostRequest request);

    Extra requestToEntity(ExtraInlineRequest request);
    List<Extra> requestToEntity(List<ExtraInlineRequest> requests);

    void updateEntity(ExtraPutRequest putRequest, @MappingTarget Extra extra);
}
