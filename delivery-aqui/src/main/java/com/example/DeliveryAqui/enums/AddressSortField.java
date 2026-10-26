package com.example.DeliveryAqui.enums;

import lombok.Getter;

@Getter
public enum AddressSortField {
    ID("id"),
    ADDRESS_LINE_1("addressLine1"),
    NEIGHBORHOOD("neighborhood"),
    CITY("city"),
    STATE("state"),
    POSTAL_CODE("postalCode"),
    NUMBER("number"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");

    private final String field;

    AddressSortField(String field) {
        this.field = field;
    }
}
