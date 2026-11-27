package com.example.DeliveryAqui.dtos.extra;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record ExtraBatchPostRequest(
        @Positive(message = "Extra Group ID must be greater than zero.")
        Long extraGroupId,

        @NotEmpty(message = "The list of extras must not be null nor empty.")
        @Valid
        List<ExtraInlineRequest> extras
) {
}
