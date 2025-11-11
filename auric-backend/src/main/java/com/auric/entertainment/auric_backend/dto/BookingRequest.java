package com.auric.entertainment.auric_backend.dto;

import jakarta.validation.constraints.*;

public record BookingRequest(
        @NotNull Long eventId,
        @NotBlank String fullName,
        @Email String email,
        @NotBlank String phone,
        @Min(1) Integer tickets
) {}