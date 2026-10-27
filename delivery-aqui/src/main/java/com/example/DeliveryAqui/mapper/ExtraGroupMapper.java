package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dtos.extra.ExtraResponse;
import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupPostRequest;
import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupPutRequest;
import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupResponse;
import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupSummaryResponse;
import com.example.DeliveryAqui.model.entity.ExtraGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExtraGroupMapper {
    ExtraGroupResponse entityToResponse(ExtraGroup extraGroup);

    @Mapping(source = "store.id", target = "storeId")
    ExtraGroupSummaryResponse entityToSummaryResponse(ExtraGroup extraGroup);

    @Mapping(source = "storeId", target = "store.id")
    ExtraGroup requestToEntity(ExtraGroupPostRequest request);

    void updateEntity(ExtraGroupPutRequest putRequest, @MappingTarget ExtraGroup product);
}
