package com.auric.entertainment.auric_backend.dto;

import com.auric.entertainment.auric_backend.entity.Lead;

import java.time.OffsetDateTime;
import java.time.LocalDate;
public record LeadResponse(
        Long id,
        String name,
        String email,
        String phone,
        String eventType,
        String city,
        LocalDate preferredDate,
        String budget,
        String message,
        OffsetDateTime createdAt
) {
    public static LeadResponse from(Lead lead) {
        return new LeadResponse(
                lead.getId(),
                lead.getName(),
                lead.getEmail(),
                lead.getPhone(),
                lead.getEventType(),
                lead.getCity(),
                lead.getPreferredDate(),
                lead.getBudget(),
                lead.getMessage(),
                lead.getCreatedAt()
        );
    }
}