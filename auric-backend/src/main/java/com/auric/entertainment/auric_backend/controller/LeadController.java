package com.auric.entertainment.auric_backend.controller;

import com.auric.entertainment.auric_backend.dto.LeadRequest;
import com.auric.entertainment.auric_backend.dto.LeadResponse;
import com.auric.entertainment.auric_backend.service.LeadService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/leads")
@CrossOrigin // optional if you ever call directly without Vite proxy
public class LeadController {

    private final LeadService service;

    public LeadController(LeadService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeadResponse create(@Valid @RequestBody LeadRequest req) {
        return service.create(req);
    }
}