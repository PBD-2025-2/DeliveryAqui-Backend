package com.example.DeliveryAqui.dtos.product;

import com.example.DeliveryAqui.dtos.extraGroup.ExtraGroupInlineRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record ProductPostRequest(
        @NotNull(message = "storeId must not be empty")
        Long storeId,

        @NotNull(message = "categoryId must not be empty")
        Long categoryId,

        @NotBlank(message = "Name must not be empty")
        String name,

        String description,
        String photo,

        @NotNull
        @Positive(message = "Sale price must be greater than zero.")
        @Digits(integer = 10, fraction = 2, message = "The sale price must have up to 10 integer digits and 2 decimal places.")
        BigDecimal salePrice,

        @NotNull
        @Positive(message = "Cost price must be greater than zero.")
        @Digits(integer = 10, fraction = 2, message = "The cost price must have up to 10 integer digits and 2 decimal places.")
        BigDecimal costPrice,

        List<Long> extraIds,

        @Valid
        List<ExtraGroupInlineRequest> newExtraGroups
) {
}