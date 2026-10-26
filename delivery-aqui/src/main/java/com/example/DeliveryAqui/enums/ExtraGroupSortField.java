package com.example.DeliveryAqui.enums;

import lombok.Getter;

@Getter
public enum ExtraGroupSortField {
    ID("id"),
    NAME("name");

    private final String field;

    ExtraGroupSortField(String field) {
        this.field = field;
    }
}
