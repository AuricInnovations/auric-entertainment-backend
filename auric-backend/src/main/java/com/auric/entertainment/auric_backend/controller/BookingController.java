package com.auric.entertainment.auric_backend.controller;

import com.auric.entertainment.auric_backend.dto.BookingRequest;
import com.auric.entertainment.auric_backend.dto.BookingResponse;
import com.auric.entertainment.auric_backend.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService service;

    @PostMapping
    public BookingResponse create(@RequestBody @Valid BookingRequest request) {
        return service.create(request);
    }
    @GetMapping("/me")
    public java.util.List<BookingResponse> myBookings(org.springframework.security.core.Authentication auth) {
        return service.listMyBookings(auth.getName()); // email as username
    }
}