package com.example.DeliveryAqui.dtos.store;

import com.example.DeliveryAqui.validation.ValidCnpj;
import com.example.DeliveryAqui.validation.ValidPhone;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.Instant;

public record StorePostRequest(
        Long address_id,
        @NotBlank(message = "Store name is required.")
        String name,
        @NotBlank(message = "CNPJ is required.")
        @ValidCnpj
        String cnpj,
        @NotBlank(message = "Phone is required.")
        @ValidPhone
        String phoneNumber,
        @NotBlank(message = "Email is required.")
        @Email(message = "Invalid email format.")
        String email
) {}
