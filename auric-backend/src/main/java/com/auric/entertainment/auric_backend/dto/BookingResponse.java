package com.auric.entertainment.auric_backend.dto;

public record BookingResponse(
        Long id,
        Long eventId,
        String eventTitle,
        Long ticketTypeId,
        String ticketTypeName,
        String fullName,
        String email,
        String phone,
        int tickets,
        String status
) {}