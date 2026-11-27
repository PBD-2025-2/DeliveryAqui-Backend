package com.example.DeliveryAqui.mapper;

import com.example.DeliveryAqui.dtos.productExtra.ProductExtraPatchRequest;
import com.example.DeliveryAqui.dtos.productExtra.ProductExtraPostRequest;
import com.example.DeliveryAqui.model.entity.ProductExtra;
import com.example.DeliveryAqui.dtos.productExtra.ProductExtraResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = { ProductMapper.class, ExtraGroupMapper.class })
public interface ProductExtraMapper {
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "extra.id", target = "extraId")
    ProductExtraResponse entityToResponse(ProductExtra productExtra);
    List<ProductExtraResponse> entityToResponse(List<ProductExtra> productExtra);

    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "extraId", target = "extra.id")
    ProductExtra requestToEntity(ProductExtraPostRequest request);
    void updateEntity(ProductExtraPatchRequest request, @MappingTarget ProductExtra productExtra);
}
