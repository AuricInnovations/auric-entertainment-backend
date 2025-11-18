-- V7: Align events schema with current code (MySQL-compatible)

-- Add new columns that do NOT already exist in V1
ALTER TABLE events
  ADD COLUMN start_time DATETIME NULL,
  ADD COLUMN end_time   DATETIME NULL,
  ADD COLUMN capacity   INT NULL,
  ADD COLUMN price      DECIMAL(10,2) NULL,
  ADD COLUMN description TEXT NULL;
  -- DO NOT add 'published' here, it already exists from V1

-- Backfill from legacy columns if they are present (start_at, end_at, price_from)
UPDATE events
SET
  start_time = COALESCE(start_time, start_at),
  end_time   = COALESCE(end_time,   end_at),
  price      = COALESCE(price,      price_from);

-- No seeding here; seeding is handled later in V10
