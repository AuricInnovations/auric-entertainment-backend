package com.auric.entertainment.auric_backend.repository;

import com.auric.entertainment.auric_backend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByPublishedTrueOrderByStartTimeAsc();
}