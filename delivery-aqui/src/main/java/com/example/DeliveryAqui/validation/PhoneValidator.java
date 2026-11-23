package com.example.DeliveryAqui.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator
        implements ConstraintValidator<ValidPhone, String> {

    @Override
    public boolean isValid(
            String phone,
            ConstraintValidatorContext context) {

        if (phone == null || phone.isBlank()) {
            return true;
        }

        String cleanedPhone = phone.replaceAll("\\D", "");

        // Remove código do país, quando informado.
        if (cleanedPhone.startsWith("55")
                && cleanedPhone.length() == 13) {
            cleanedPhone = cleanedPhone.substring(2);
        }

        // DDD + telefone: 10 ou 11 dígitos.
        if (!cleanedPhone.matches("\\d{10,11}")) {
            return false;
        }

        // DDD brasileiro: 11 a 99.
        int ddd = Integer.parseInt(cleanedPhone.substring(0, 2));

        return ddd >= 11 && ddd <= 99;
    }
}

