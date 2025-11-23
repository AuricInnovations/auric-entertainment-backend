-- V15__create_event_ticket_types.sql
CREATE TABLE event_ticket_types (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  event_id      BIGINT NOT NULL,
  name          VARCHAR(100) NOT NULL,   -- "VIP Boxes", "Premium Balcony"
  capacity      INT NOT NULL,           -- seats available in this category
  price         DECIMAL(10,2) NOT NULL, -- price per ticket
  sort_order    INT DEFAULT 0,
  is_active     TINYINT(1) NOT NULL DEFAULT 1,
  CONSTRAINT fk_tickettype_event
    FOREIGN KEY (event_id) REFERENCES events(id)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;