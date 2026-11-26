package com.example.DeliveryAqui.dtos.extraGroup;

import com.example.DeliveryAqui.dtos.extra.ExtraInlineRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;

import java.util.List;

public record ExtraGroupPostRequest(
        @Positive(message = "Store ID must be greater than zero.")
        Long storeId,

        @NotBlank(message = "Name number may not be empty.")
        @Size(max = 255)
        String name,

        @NotNull(message = "Min select is required")
        @PositiveOrZero(message = "Min select must be zero or greater.")
        Short minSelect,

        @NotNull(message = "Max select is required")
        @Positive(message = "Max select must be zero or greater.")
        Short maxSelect,

        List<ExtraInlineRequest>newExtras
) {
        @JsonIgnore
        @AssertTrue(message = "Min select must be less than o equal to max select")
        public boolean isSelectRangeValid() {
                if (minSelect == null || maxSelect == null) return false;
                return minSelect <= maxSelect;
        }
}
