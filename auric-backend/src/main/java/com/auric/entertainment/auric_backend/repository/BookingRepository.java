package com.auric.entertainment.auric_backend.repository;

import com.auric.entertainment.auric_backend.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByEmailOrderByCreatedAtDesc(String email);

    Page<Booking> findByEventId(Long eventId, Pageable pageable);

    @Query("""
        select coalesce(sum(b.tickets), 0)
        from Booking b
        where b.ticketType.id = :ticketTypeId
          and b.status = 'CONFIRMED'
    """)
    Integer sumConfirmedTicketsByTicketType(@Param("ticketTypeId") Long ticketTypeId);

    @Query("""
        select coalesce(sum(b.tickets), 0)
        from Booking b
        where b.ticketType.id = :ticketTypeId
          and b.status = 'PENDING_PAYMENT'
          and b.createdAt >= :cutoff
    """)
    Integer sumHeldTicketsByTicketType(
            @Param("ticketTypeId") Long ticketTypeId,
            @Param("cutoff") java.time.OffsetDateTime cutoff
    );
}
