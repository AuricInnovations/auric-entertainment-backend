package com.auric.entertainment.auric_backend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record UpsertTicketTypeRequest(
        @NotBlank String name,
        @Min(1) int capacity,
        @DecimalMin("0.0") BigDecimal price,
        Integer unitSize,         // NEW
        String description,       // NEW
        Integer sortOrder,
        Boolean isActive
) {}