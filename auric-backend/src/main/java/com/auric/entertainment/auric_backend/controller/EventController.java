package com.auric.entertainment.auric_backend.controller;

import com.auric.entertainment.auric_backend.entity.Event;
import com.auric.entertainment.auric_backend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService service;

    @GetMapping
    public List<Event> list() { return service.listPublished(); }

    @GetMapping("/{id}")
    public Event get(@PathVariable Long id) { return service.get(id); }
}
