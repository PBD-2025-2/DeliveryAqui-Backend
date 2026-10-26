package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dtos.product.ProductPostRequest;
import com.example.DeliveryAqui.dtos.product.ProductPutRequest;
import com.example.DeliveryAqui.dtos.product.ProductResponse;
import com.example.DeliveryAqui.dtos.product.ProductSummaryResponse;
import com.example.DeliveryAqui.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "category.id", target = "categoryId")
    ProductSummaryResponse entityToSummaryResponse(Product product);

    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "category.id", target = "categoryId")
    ProductResponse entityToResponse(Product product);
    List<ProductSummaryResponse> entityListToResponse(List<Product> products);

    @Mapping(source = "storeId", target = "store.id")
    @Mapping(source = "categoryId", target = "category.id")
    Product requestToEntity(ProductPostRequest request);

    void updateEntity(ProductPutRequest putRequest, @MappingTarget Product product);
}
