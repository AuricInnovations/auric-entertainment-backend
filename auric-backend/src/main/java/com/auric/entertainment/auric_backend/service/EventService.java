package com.auric.entertainment.auric_backend.service;

import com.auric.entertainment.auric_backend.dto.EventUpdateRequest;
import com.auric.entertainment.auric_backend.entity.Event;
import com.auric.entertainment.auric_backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository repo;

    public List<Event> listPublished() {
        return repo.findByPublishedTrueOrderByStartTimeAsc();
    }

    public Event get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found: " + id));
    }

    @Transactional
    public Event create(Event event) {
        // never trust incoming ID
        event.setId(null);
        if (event.getPublished() == null) {
            event.setPublished(Boolean.TRUE);
        }

        return repo.save(event);
    }

    @Transactional
    public Event update(Long id, EventUpdateRequest req) {
        Event existing = get(id);

        if (req.title() != null)
            existing.setTitle(req.title());
        if (req.venue() != null)
            existing.setVenue(req.venue());
        if (req.startTime() != null)
            existing.setStartTime(req.startTime().atOffset(java.time.ZoneOffset.UTC));
        if (req.endTime() != null)
            existing.setEndTime(req.endTime().atOffset(java.time.ZoneOffset.UTC));
        if (req.capacity() != null)
            existing.setCapacity(req.capacity());
        if (req.price() != null)
            existing.setPrice(req.price());
        if (req.description() != null)
            existing.setDescription(req.description());
        if (req.published() != null)
            existing.setPublished(req.published());
        if (req.coverImageUrl() != null)
            existing.setCoverImageUrl(req.coverImageUrl());

        return repo.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id))
            throw new IllegalArgumentException("Event not found");
        repo.deleteById(id);
    }

    public List<Event> listAll() {
        return repo.findAll();
    }
}