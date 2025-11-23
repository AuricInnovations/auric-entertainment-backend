ALTER TABLE event_ticket_types
  ADD COLUMN description TEXT NULL AFTER name;
ALTER TABLE event_ticket_types
  ADD COLUMN unit_size INT NULL AFTER capacity;

