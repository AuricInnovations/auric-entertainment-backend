package com.auric.entertainment.auric_backend.dto;

import java.math.BigDecimal;

public record TicketTypeResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer capacity,
        Integer unitSize,
        Integer sortOrder
) {}
