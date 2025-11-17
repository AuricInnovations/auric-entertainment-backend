package com.auric.entertainment.auric_backend.service;

import com.auric.entertainment.auric_backend.dto.LeadRequest;
import com.auric.entertainment.auric_backend.dto.LeadResponse;
import com.auric.entertainment.auric_backend.entity.Lead;
import com.auric.entertainment.auric_backend.repository.LeadRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LeadService {

    private final LeadRepository repo;

    public LeadService(LeadRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public LeadResponse create(LeadRequest req) {
        Lead lead = new Lead();
        lead.setName(req.name());
        lead.setEmail(req.email());
        lead.setPhone(req.phone());
        lead.setEventType(req.eventType());
        lead.setCity(req.city());
        lead.setPreferredDate(req.preferredDate());
        lead.setBudget(req.budget());
        lead.setMessage(req.message());
        Lead saved = repo.save(lead);
        return LeadResponse.from(saved);
    }
}