package com.auric.entertainment.auric_backend.dto;

public record TicketAvailabilityResponse(
        Long ticketTypeId,
        String ticketTypeName,
        Integer capacity,
        Integer confirmed,
        Integer held,
        Integer available
) {}