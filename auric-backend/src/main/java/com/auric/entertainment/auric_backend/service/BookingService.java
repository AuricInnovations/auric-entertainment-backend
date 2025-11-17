package com.auric.entertainment.auric_backend.service;

import com.auric.entertainment.auric_backend.dto.BookingRequest;
import com.auric.entertainment.auric_backend.dto.BookingResponse;
import com.auric.entertainment.auric_backend.entity.Booking;
import com.auric.entertainment.auric_backend.entity.Event;
import com.auric.entertainment.auric_backend.repository.BookingRepository;
import com.auric.entertainment.auric_backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepo;
    private final EventRepository eventRepo;

    @Transactional
    public BookingResponse create(BookingRequest req) {
        Event ev = eventRepo.findById(req.eventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        // Capacity enforcement (simple P1 approach)
        int reserved = bookingRepo.sumTicketsByEventId(ev.getId()).orElse(0);
        int remaining = ev.getCapacity() - reserved;
        if (req.tickets() > remaining) {
            throw new IllegalArgumentException("Not enough capacity. Remaining: " + remaining);
        }

        Booking b = new Booking();
        b.setEvent(ev);
        b.setFullName(req.fullName());
        b.setEmail(req.email());
        b.setPhone(req.phone());
        b.setTickets(req.tickets());
        b.setStatus("PENDING");
        b.setCreatedAt(OffsetDateTime.now());

        bookingRepo.save(b);
        return toDto(b);
    }
    @Transactional(readOnly = true)
    public List<BookingResponse> listMyBookings(String email) {
        return bookingRepo.findByEmailOrderByCreatedAtDesc(email)
                .stream().map(this::toDto).toList();
    }
    @Transactional(readOnly = true)
    public Page<BookingResponse> listAll(Pageable pageable, Long eventId) {
        Page<Booking> page;
        if (eventId != null) {
            page = bookingRepo.findByEventId(eventId, pageable);
        } else {
            page = bookingRepo.findAll(pageable);
        }
        return page.map(this::toDto);
    }
    @Transactional(readOnly = true)
    public BookingResponse getOne(Long id) {
        return bookingRepo.findById(id).map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }
    private BookingResponse toDto(Booking b) {
        return new BookingResponse(
                b.getId(),
                b.getEvent().getId(),
                b.getFullName(),
                b.getEmail(),
                b.getPhone(),
                b.getTickets()
        );
    }
}
