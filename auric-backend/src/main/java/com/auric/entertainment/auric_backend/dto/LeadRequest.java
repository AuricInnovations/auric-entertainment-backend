package com.auric.entertainment.auric_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
public record LeadRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        String phone,
        String eventType,
        String city,
        LocalDate preferredDate,
        String budget,
        String message
) {}
