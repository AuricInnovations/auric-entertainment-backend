package com.auric.entertainment.auric_backend.controller;

import com.auric.entertainment.auric_backend.dto.EventUpdateRequest;
import com.auric.entertainment.auric_backend.entity.Event;
import com.auric.entertainment.auric_backend.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService service;

    @PostMapping
    public Event create(@RequestBody Event req) { return service.create(req); }

    @PutMapping("/{id}")
    public Event update(@PathVariable Long id, @RequestBody EventUpdateRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }

    @GetMapping
    public List<Event> listAll() {
        return service.listAll();
    }
}