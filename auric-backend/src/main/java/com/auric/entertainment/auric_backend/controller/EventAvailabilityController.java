package com.auric.entertainment.auric_backend.controller;

import com.auric.entertainment.auric_backend.dto.TicketAvailabilityResponse;
import com.auric.entertainment.auric_backend.repository.BookingRepository;
import com.auric.entertainment.auric_backend.repository.EventRepository;
import com.auric.entertainment.auric_backend.repository.EventTicketTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EventAvailabilityController {

    private final EventRepository eventRepo;
    private final EventTicketTypeRepository ticketTypeRepo;
    private final BookingRepository bookingRepo;

    @GetMapping("/events/{eventId}/availability")
    public List<TicketAvailabilityResponse> getAvailability(@PathVariable Long eventId) {
        // ensure event exists (nice error if not)
        var event = eventRepo.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        var now = OffsetDateTime.now();
        var holdWindowMinutes = 15L;


        var ticketTypes = ticketTypeRepo.findByEventIdAndActiveTrueOrderBySortOrderAsc(event.getId());

        return ticketTypes.stream()
                .map(tt -> {
                    int capacity = tt.getCapacity() != null ? tt.getCapacity() : 0;

                    int confirmed = safeInt(
                            bookingRepo.sumConfirmedTicketsByTicketType(tt.getId())
                    );
                    int held = safeInt(
                            bookingRepo.sumHeldTicketsByTicketType(
                                    tt.getId(),
                                    now.minusMinutes(holdWindowMinutes)
                            )
                    );

                    int available = capacity - confirmed - held;
                    if (available < 0) available = 0;

                    return new TicketAvailabilityResponse(
                            tt.getId(),
                            tt.getName(),
                            capacity,
                            confirmed,
                            held,
                            available
                    );
                })
                .toList();
    }

    private int safeInt(Integer i) {
        return i != null ? i : 0;
    }
}
