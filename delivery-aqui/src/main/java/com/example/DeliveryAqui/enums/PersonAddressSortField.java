package com.example.DeliveryAqui.enums;

import lombok.Getter;

@Getter
public enum PersonAddressSortField {
    ID("id"),
    IS_FAVORITE("isFavorite"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");

    private final String field;

    PersonAddressSortField(String fieldName) {
        this.field = fieldName;
    }
}
