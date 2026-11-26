package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dtos.productExtra.ProductExtraPostRequest;
import com.example.DeliveryAqui.model.entity.ProductExtra;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ProductMapper.class, ExtraGroupMapper.class })
public interface ProductExtraMapper {
    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "extraId", target = "extra.id")
    ProductExtra requestToEntity(ProductExtraPostRequest request);
}
