package com.example.DeliveryAqui.util;

import com.example.DeliveryAqui.dtos.shared.PaginatedResponse;
import org.springframework.data.domain.Page;

public class PaginationUtils {
    private PaginationUtils () {}

    public static <T> PaginatedResponse<T> buildPaginatedResponse(Page<T> page) {
        return new PaginatedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getSize(),
                page.hasNext(),
                page.hasPrevious()
        );
    }
}
