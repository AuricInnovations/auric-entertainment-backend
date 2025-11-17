package com.auric.entertainment.auric_backend.repository;

import com.auric.entertainment.auric_backend.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByEmailOrderByCreatedAtDesc(String email);
    @Query("select coalesce(sum(b.tickets), 0) from Booking b where b.event.id = :eventId")
    Optional<Integer> sumTicketsByEventId(Long eventId);
    Page<Booking> findByEventId(Long eventId, Pageable pageable);
}
