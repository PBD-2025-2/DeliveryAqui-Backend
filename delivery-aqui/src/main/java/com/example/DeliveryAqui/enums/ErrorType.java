package com.example.DeliveryAqui.enums;

import lombok.Getter;

@Getter
public enum ErrorType {
    VALIDATION_ERROR("validation-error"),
    ADDRESS_NOT_FOUND("address-not-found"),
    ADDRESS_IN_USE("address-in-use"),
    PERSON_NOT_FOUND("person-not-found"),
    PERSON_ADDRESS_NOT_FOUND("person-address-not-found"),
    PERSON_ADDRESS_ALREADY_EXISTS("person-address-already-exists");

    private static final String PREFIX_URI = "/errors/";
    private final String uri;

    ErrorType(String uri) {
        this.uri = PREFIX_URI + uri;
    }
}
