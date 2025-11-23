package com.auric.entertainment.auric_backend.controller;

import com.auric.entertainment.auric_backend.dto.BookingResponse;
import com.auric.entertainment.auric_backend.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/bookings")
@RequiredArgsConstructor
public class AdminBookingController {

    private final BookingService service;

    @GetMapping
    public Page<BookingResponse> list(
            @RequestParam(required = false) Long eventId,
            Pageable pageable
    ) {
        return service.listAll(pageable, eventId);
    }

    @GetMapping("/{id}")
    public BookingResponse get(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PatchMapping("/{id}/confirm")
    public BookingResponse confirm(@PathVariable Long id) {
        return service.confirmBooking(id);
    }
}
