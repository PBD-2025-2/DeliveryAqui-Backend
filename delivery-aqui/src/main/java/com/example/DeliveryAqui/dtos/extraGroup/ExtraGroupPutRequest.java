package com.example.DeliveryAqui.dtos.extraGroup;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;

public record ExtraGroupPutRequest(
        @NotBlank(message = "Name number may not be empty.")
        @Size(max = 255)
        String name,

        @NotNull(message = "Min select is required")
        @PositiveOrZero(message = "Min select must be zero or greater.")
        Short minSelect,

        @NotNull(message = "Max select is required")
        @Positive(message = "Max select must be zero or greater.")
        Short maxSelect
) {
        @JsonIgnore
        @AssertTrue(message = "Min select must be less than o equal to max select")
        public boolean isSelectRangeValid() {
            if (minSelect == null || maxSelect == null) return false;
            return minSelect <= maxSelect;
        }
}
