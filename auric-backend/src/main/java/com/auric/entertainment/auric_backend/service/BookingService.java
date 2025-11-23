package com.auric.entertainment.auric_backend.service;

import com.auric.entertainment.auric_backend.dto.BookingRequest;
import com.auric.entertainment.auric_backend.dto.BookingResponse;
import com.auric.entertainment.auric_backend.dto.CreateBookingRequest;
import com.auric.entertainment.auric_backend.entity.Booking;
import com.auric.entertainment.auric_backend.entity.Event;
import com.auric.entertainment.auric_backend.entity.EventTicketType;
import com.auric.entertainment.auric_backend.repository.BookingRepository;
import com.auric.entertainment.auric_backend.repository.EventRepository;
import com.auric.entertainment.auric_backend.repository.EventTicketTypeRepository;
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
    private final EventTicketTypeRepository ticketTypeRepo;
    private final WhatsappNotificationService whatsappNotificationService;

    @Transactional
    public BookingResponse create(CreateBookingRequest req) {
        Event event = eventRepo.findById(req.eventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        EventTicketType tt = ticketTypeRepo.findById(req.ticketTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Ticket category not found"));

        if (!tt.getEvent().getId().equals(event.getId())) {
            throw new IllegalArgumentException("Ticket category does not belong to this event");
        }

        int requested = req.tickets();

        // ---- capacity calculation: CONFIRMED + active holds ----
        var now = OffsetDateTime.now();
        var holdWindowMinutes = 15L; // change if you want

        int confirmed = bookingRepo
                .sumConfirmedTicketsByTicketType(tt.getId());

        int held = bookingRepo
                .sumHeldTicketsByTicketType(
                        tt.getId(),
                        now.minusMinutes(holdWindowMinutes)
                );

        int used = confirmed + held;

        if (used + requested > tt.getCapacity()) {
            throw new IllegalStateException("Not enough seats in this category");
        }

        // ---- create hold booking ----
        Booking b = new Booking();
        b.setEvent(event);
        b.setTicketType(tt);
        b.setFullName(req.fullName());
        b.setEmail(req.email());
        b.setPhone(req.phone());
        b.setTickets(requested);
        b.setStatus("PENDING_PAYMENT");   // <-- hold, not confirmed
        b.setCreatedAt(now);

        Booking saved = bookingRepo.save(b);

        // Don’t let WhatsApp failure break booking
        try {
            whatsappNotificationService.sendBookingAlert(saved);
        } catch (Exception ex) {
            // log only
            System.err.println("WhatsApp failed: " + ex.getMessage());
        }

        return toDto(saved);
    }
    @Transactional
    public BookingResponse confirmBooking(Long id) {
        Booking b = bookingRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if (!"PENDING_PAYMENT".equals(b.getStatus())) {
            throw new IllegalStateException("Only PENDING_PAYMENT bookings can be confirmed");
        }

        b.setStatus("CONFIRMED");
        return toDto(b);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> listMyBookings(String email) {
        return bookingRepo.findByEmailOrderByCreatedAtDesc(email)
                .stream()
                .map(this::toDto)
                .toList();
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
        return bookingRepo.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
    }

    private BookingResponse toDto(Booking b) {
        return new BookingResponse(
                b.getId(),
                b.getEvent().getId(),
                b.getEvent().getTitle(),
                b.getTicketType() != null ? b.getTicketType().getId() : null,
                b.getTicketType() != null ? b.getTicketType().getName() : null,
                b.getFullName(),
                b.getEmail(),
                b.getPhone(),
                b.getTickets(),
                b.getStatus()
        );
    }
}
