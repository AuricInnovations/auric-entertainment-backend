-- V16__add_booking_ticket_type.sql
ALTER TABLE bookings
  ADD COLUMN ticket_type_id BIGINT NULL;

ALTER TABLE bookings
  ADD CONSTRAINT fk_booking_tickettype
    FOREIGN KEY (ticket_type_id)
    REFERENCES event_ticket_types(id);