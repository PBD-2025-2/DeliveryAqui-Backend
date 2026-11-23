package com.example.DeliveryAqui.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Locale;

public class CnpjValidator implements ConstraintValidator<ValidCnpj, String> {

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {

        if (cnpj == null || cnpj.isBlank()) {
            return true;
        }

        cnpj = cnpj.replaceAll("[./\\-\\s]", "")
                .toUpperCase(Locale.ROOT);

        if (!cnpj.matches("[A-Z0-9]{12}[0-9]{2}")) {
            return false;
        }

        if (cnpj.chars().distinct().count() == 1) {
            return false;
        }

        int firstDigit = calculateDigit(cnpj.substring(0, 12));

        int secondDigit = calculateDigit(
                cnpj.substring(0, 12) + firstDigit
        );

        String verifiedDigits = "" + firstDigit + secondDigit;

        return cnpj.substring(12).equals(verifiedDigits);
    }

    private int calculateDigit(String value) {

        int[] weights = {2, 3, 4, 5, 6, 7, 8, 9};

        int sum = 0;
        int weightIndex = 0;

        for (int i = value.length() - 1; i >= 0; i--) {

            int characterValue = value.charAt(i) - 48;

            sum += characterValue * weights[weightIndex];

            weightIndex = (weightIndex + 1) % weights.length;
        }

        int remainder = sum % 11;

        return remainder < 2 ? 0 : 11 - remainder;
    }
}
