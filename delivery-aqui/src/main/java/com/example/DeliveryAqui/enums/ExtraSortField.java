package com.example.DeliveryAqui.enums;

import lombok.Getter;

@Getter
public enum ExtraSortField {
    ID("id"),
    NAME("name"),
    PRICE("price"),
    IS_AVAILABLE("is_available");

    private final String field;

    ExtraSortField(String field) {
        this.field = field;
    }
}
