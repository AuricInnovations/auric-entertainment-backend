package com.auric.entertainment.auric_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter @Setter
@Table(name = "event_ticket_types")
public class EventTicketType {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    private String name;
    private String description;

    private BigDecimal price;
    private Integer capacity;
    private Integer unitSize;
    private Integer sortOrder;

    private Boolean active = true;   // <– Hibernate expects column "active"
}
