package com.auric.entertainment.auric_backend.controller;


import com.auric.entertainment.auric_backend.dto.TicketTypeResponse;
import com.auric.entertainment.auric_backend.dto.UpsertTicketTypeRequest;
import com.auric.entertainment.auric_backend.service.TicketTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/events/{eventId}/ticket-types")
@RequiredArgsConstructor
public class AdminEventTicketTypeController {

    private final TicketTypeService service;

    @GetMapping
    public List<TicketTypeResponse> list(@PathVariable Long eventId) {
        return service.listByEvent(eventId);
    }

    @PostMapping
    public TicketTypeResponse create(
            @PathVariable Long eventId,
            @Valid @RequestBody UpsertTicketTypeRequest req
    ) {
        return service.create(eventId, req);
    }

    @PutMapping("/{id}")
    public TicketTypeResponse update(
            @PathVariable Long eventId,
            @PathVariable Long id,
            @Valid @RequestBody UpsertTicketTypeRequest req
    ) {
        return service.update(eventId, id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long eventId, @PathVariable Long id) {
        service.delete(eventId, id);
    }
}