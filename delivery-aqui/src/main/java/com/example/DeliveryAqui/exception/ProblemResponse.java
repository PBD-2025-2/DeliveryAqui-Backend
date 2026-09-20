package com.example.DeliveryAqui.exception;

import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public class ProblemResponse extends ProblemDetail {
    public ProblemResponse(String detail, @NonNull HttpStatus status, String type) {
        super(status.value());
        setDetail(detail);
        setType(URI.create(type));
        setProperty("timestamp", Instant.now());
    }

    public ProblemResponse(@NonNull HttpStatus status, String detail, String type, Map<String, List<String>> errors) {
        super(status.value());
        setDetail(detail);
        setType(URI.create(type));
        setProperty("timestamp", Instant.now());
        setProperty("errors", errors);
    }
}
