package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dtos.extraGroup.*;
import com.example.DeliveryAqui.model.entity.ExtraGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = { StoreMapper.class, ExtraMapper.class })
public interface ExtraGroupMapper {
    ExtraGroupResponse entityToResponse(ExtraGroup extraGroup);

    @Mapping(source = "store.id", target = "storeId")
    ExtraGroupSummaryResponse entityToSummaryResponse(ExtraGroup extraGroup);

    @Mapping(source = "store.id", target = "storeId")
    ExtraGroupDetailResponse entityToDetailResponse(ExtraGroup extraGroup);
    List<ExtraGroupDetailResponse> entityToDetailResponse(List<ExtraGroup> extraGroups);

    @Mapping(target = "extras", ignore = true)
    @Mapping(source = "storeId", target = "store.id")
    ExtraGroup requestToEntity(ExtraGroupPostRequest request);

    @Mapping(target = "extras", ignore = true)
    ExtraGroup requestToEntity(ExtraGroupInlineRequest request);

    @Mapping(target = "extras", ignore = true)
    void updateEntity(ExtraGroupPutRequest putRequest, @MappingTarget ExtraGroup product);
}
