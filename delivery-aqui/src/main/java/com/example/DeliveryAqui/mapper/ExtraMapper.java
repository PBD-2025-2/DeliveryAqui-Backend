package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dtos.extra.ExtraPostRequest;
import com.example.DeliveryAqui.dtos.extra.ExtraPutRequest;
import com.example.DeliveryAqui.dtos.extra.ExtraResponse;
import com.example.DeliveryAqui.dtos.extra.ExtraSummaryResponse;
import com.example.DeliveryAqui.model.entity.Extra;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExtraMapper {
    ExtraResponse entityToResponse(Extra extra);

    @Mapping(source = "extraGroup.id", target = "extraGroupId")
    ExtraSummaryResponse entityToSummaryResponse(Extra extra);

    @Mapping(source = "extraGroupId", target = "extraGroup.id")
    Extra requestToEntity(ExtraPostRequest request);

    void updateEntity(ExtraPutRequest putRequest, @MappingTarget Extra extra);
}
