package com.auric.entertainment.auric_backend.service;

import com.auric.entertainment.auric_backend.dto.TicketTypeResponse;
import com.auric.entertainment.auric_backend.dto.UpsertTicketTypeRequest;
import com.auric.entertainment.auric_backend.entity.Event;
import com.auric.entertainment.auric_backend.entity.EventTicketType;
import com.auric.entertainment.auric_backend.repository.EventRepository;
import com.auric.entertainment.auric_backend.repository.EventTicketTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor

public class TicketTypeService {

    private final EventTicketTypeRepository repo;
    private final EventRepository eventRepo;



    public List<TicketTypeResponse> listForEvent(Long eventId) {
        return repo.findByEventIdAndActiveTrueOrderBySortOrderAsc(eventId)
                .stream()
                .map(t -> new TicketTypeResponse(
                        t.getId(),
                        t.getName(),
                        t.getDescription(),
                        t.getPrice(),
                        t.getCapacity(),
                        t.getUnitSize(),
                        t.getSortOrder()
                ))
                .toList();
    }

    private TicketTypeResponse toDto(EventTicketType t) {
        return new TicketTypeResponse(
                t.getId(),
                t.getName(),
                t.getDescription(),
                t.getPrice(),
                t.getCapacity(),
                t.getUnitSize(),
                t.getSortOrder()
        );
    }

    // ==== ADMIN (manage categories per event) ====

    @Transactional
    public TicketTypeResponse create(Long eventId, UpsertTicketTypeRequest req) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        EventTicketType t = new EventTicketType();
        t.setEvent(event);
        t.setName(req.name());
        t.setDescription(null);
        t.setPrice(req.price());
        t.setCapacity(req.capacity());
        t.setUnitSize(null);
        t.setSortOrder(req.sortOrder());
        t.setActive(Boolean.TRUE.equals(req.isActive()));

        return toDto(repo.save(t));
    }

    @Transactional
    public TicketTypeResponse update(Long eventId, Long id, UpsertTicketTypeRequest req) {
        EventTicketType t = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket type not found"));

        if (!t.getEvent().getId().equals(eventId)) {
            throw new IllegalArgumentException("Ticket type does not belong to this event");
        }

        t.setName(req.name());
        t.setDescription(req.description());
        t.setPrice(req.price());
        t.setCapacity(req.capacity());
        t.setUnitSize(req.unitSize());
        t.setSortOrder(req.sortOrder());
        t.setActive(Boolean.TRUE.equals(req.isActive()));

        return toDto(repo.save(t));
    }

    @Transactional
    public void delete(Long eventId, Long id) {
        EventTicketType t = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket type not found"));

        if (!t.getEvent().getId().equals(eventId)) {
            throw new IllegalArgumentException("Ticket type does not belong to this event");
        }

        repo.delete(t);
    }

    @Transactional(readOnly = true)
    public List<TicketTypeResponse> listByEvent(Long eventId) {
        return repo.findByEventIdAndActiveTrueOrderBySortOrderAsc(eventId)
                .stream()
                .map(this::toDto)
                .toList();
    }
}
