package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dtos.product.*;
import com.example.DeliveryAqui.model.entity.Extra;
import com.example.DeliveryAqui.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ExtraMapper.class })
public interface ProductMapper {
    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "category.id", target = "categoryId")
    ProductResponse entityToResponse(Product product);

    @Mapping(source = "product.store.id", target = "storeId")
    @Mapping(source = "product.category.id", target = "categoryId")
    ProductDetailResponse entityToDetailResponse(Product product, List<Extra> extras);

    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "category.id", target = "categoryId")
    ProductSummaryResponse entityToSummaryResponse(Product product);

    @Mapping(source = "storeId", target = "store.id")
    @Mapping(source = "categoryId", target = "category.id")
    Product requestToEntity(ProductPostRequest request);

    void updateEntity(ProductPutRequest putRequest, @MappingTarget Product product);
}
