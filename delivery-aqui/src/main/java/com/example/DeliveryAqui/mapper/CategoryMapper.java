package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dtos.category.CategoryPostRequest;
import com.example.DeliveryAqui.dtos.category.CategoryPutRequest;
import com.example.DeliveryAqui.dtos.category.CategoryResponse;
import com.example.DeliveryAqui.dtos.category.CategorySummaryResponse;
import com.example.DeliveryAqui.model.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "parent.id", target = "parentId")
    CategorySummaryResponse entityToSummaryResponse(Category category);

    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "parent.id", target = "parentId")
    CategoryResponse entityToResponse(Category category);

    List<CategorySummaryResponse> entityListToResponse(List<Category> categories);

    @Mapping(source = "storeId", target = "store.id")
    Category requestToEntity(CategoryPostRequest request);

    void updateEntity(CategoryPutRequest putRequest, @MappingTarget Category category);
}
