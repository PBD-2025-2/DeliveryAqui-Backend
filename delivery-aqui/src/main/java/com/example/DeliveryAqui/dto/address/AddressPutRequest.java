package com.example.DeliveryAqui.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AddressPutRequest(
        @NotBlank(message = "Address Line 1 may not be empty.")
        @Size(max = 255)
        String addressLine1,

        @Size(max = 100)
        String addressLine2,

        @NotBlank(message = "Neighborhood number may not be empty.")
        @Size(max = 255)
        String neighborhood,

        @NotBlank(message = "City may not be empty.")
        @Size(max = 50)
        String city,

        @NotBlank(message = "State may not be empty.")
        @Size(max = 50)
        String state,

        @NotBlank(message = "Postal number may not be empty.")
        @Pattern(regexp = "\\d{8}", message = "Postal number must contain 8 numeric digits.")
        String postalCode,

        @Positive(message = "Number must be greater than zero.")
        Integer number
) {
}
