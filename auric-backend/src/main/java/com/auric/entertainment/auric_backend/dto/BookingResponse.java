package com.auric.entertainment.auric_backend.dto;

public record BookingResponse(
        Long id,
        Long eventId,
        String fullName,
        String email,
        String phone,
        Integer tickets
) {}
