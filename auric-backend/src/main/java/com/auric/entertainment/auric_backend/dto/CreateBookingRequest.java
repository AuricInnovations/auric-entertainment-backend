package com.auric.entertainment.auric_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBookingRequest(
        @NotNull Long eventId,
        @NotNull Long ticketTypeId,   // category (VIP Box, Premium Seats, etc.)
        @NotBlank String fullName,
        @Email @NotBlank String email,
        @NotBlank String phone,
        @Min(1) int tickets
) {}
