package com.example.DeliveryAqui.dtos.extraGroup;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record ExtraGroupBatchPostRequest(
        @Positive(message = "Store ID must be greater than zero.")
        Long storeId,

        @NotEmpty(message = "The list of extras must not be null nor empty.")
        @Valid
        List<ExtraGroupInlineRequest> extraGroups
) {
}
