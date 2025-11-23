package com.auric.entertainment.auric_backend.controller;

import com.auric.entertainment.auric_backend.dto.TicketTypeResponse;
import com.auric.entertainment.auric_backend.service.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/events/{eventId}/tickets")
@RequiredArgsConstructor
public class EventTicketController {

    private final TicketTypeService service;

    @GetMapping
    public List<TicketTypeResponse> list(@PathVariable Long eventId) {
        return service.listForEvent(eventId);
    }
}
