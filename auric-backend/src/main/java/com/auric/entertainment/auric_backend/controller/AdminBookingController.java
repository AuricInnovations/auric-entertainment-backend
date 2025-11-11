package com.auric.entertainment.auric_backend.controller;

import com.auric.entertainment.auric_backend.dto.BookingResponse;
import com.auric.entertainment.auric_backend.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/bookings")
@RequiredArgsConstructor
public class AdminBookingController {
    private final BookingService service;

    @GetMapping
    public Page<BookingResponse> list(Pageable pageable) {
        return service.listAll(pageable);
    }

    @GetMapping("/{id}")
    public BookingResponse get(@PathVariable Long id) {
        return service.getOne(id);
    }
}
