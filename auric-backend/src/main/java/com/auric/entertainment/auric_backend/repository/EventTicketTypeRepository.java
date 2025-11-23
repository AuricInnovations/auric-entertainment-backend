package com.auric.entertainment.auric_backend.repository;

import com.auric.entertainment.auric_backend.entity.EventTicketType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventTicketTypeRepository extends JpaRepository<EventTicketType, Long> {
    List<EventTicketType> findByEventIdAndActiveTrueOrderBySortOrderAsc(Long eventId);
}
