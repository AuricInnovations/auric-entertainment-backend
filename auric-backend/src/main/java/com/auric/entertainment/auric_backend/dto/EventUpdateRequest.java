package com.auric.entertainment.auric_backend.dto;

public record EventUpdateRequest(
        String title,
        String venue,
        java.time.LocalDateTime startTime,
        java.time.LocalDateTime endTime,
        Integer capacity,
        java.math.BigDecimal price,
        String description,
        Boolean published
) {}
