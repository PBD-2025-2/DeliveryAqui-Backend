package com.example.DeliveryAqui.dtos.person;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record PersonPostRequest(
        @CPF(message = "Invalid CPF")
        String cpf,

        @NotBlank(message = "firstName must not be blank")
        @Size(max = 50)
        String firstName,

        @NotBlank(message = "lastName must not be blank")
        @Size(max = 50)
        String lastName,

        @NotBlank(message = "phoneNumber must not be blank")
        @Size(max = 20)
        String phoneNumber,

        @NotBlank(message = "Email must not be blank")
        String email
) {}
