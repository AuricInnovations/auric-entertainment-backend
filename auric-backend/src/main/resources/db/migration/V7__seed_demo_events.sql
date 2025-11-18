-- Align schema to what the code expects (MySQL 8+)
ALTER TABLE events
  ADD COLUMN  start_time DATETIME NULL,
  ADD COLUMN end_time   DATETIME NULL,
  ADD COLUMN  capacity   INT NULL,
  ADD COLUMN  price      DECIMAL(10,2) NULL,
  ADD COLUMN  description TEXT NULL,
  ADD COLUMN  published  TINYINT(1) NOT NULL DEFAULT 1;

-- Backfill from legacy columns if they existed
UPDATE events
SET
  start_time = COALESCE(start_time, start_at),
  end_time   = COALESCE(end_time,   end_at),
  price      = COALESCE(price,      price_from);

-- Optional: drop legacy columns after you’ve confirmed data copy
-- ALTER TABLE events DROP COLUMN start_at, DROP COLUMN end_at, DROP COLUMN price_from;

-- Seed demo events (idempotent)
INSERT INTO events (title, venue, start_time, end_time, capacity, price, description, published)
VALUES
('Hadagasma Live', 'Stadium KL',       '2025-05-02 19:00:00', '2025-05-02 22:00:00', 5000, 150.00, 'Main concert', 1),
('Food & Culture Fest', 'Auric Grounds','2025-06-15 10:00:00', '2025-06-15 22:00:00', 3000,  20.00, 'Day festival', 1)
ON DUPLICATE KEY UPDATE
  venue=VALUES(venue),
  start_time=VALUES(start_time),
  end_time=VALUES(end_time),
  capacity=VALUES(capacity),
  price=VALUES(price),
  description=VALUES(description),
  published=VALUES(published);
