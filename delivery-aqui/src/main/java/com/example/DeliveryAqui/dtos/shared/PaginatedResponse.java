package com.example.DeliveryAqui.dtos.shared;

import java.util.List;

public record PaginatedResponse<T>(
        List<T> data,
        int currentPage,
        int totalPages,
        long totalItems,
        int pageSize,
        boolean hasNext,
        boolean hasPrevious
) {
}
