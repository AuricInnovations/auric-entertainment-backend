-- Add missing columns (idempotent guards where helpful)
ALTER TABLE events
  ADD COLUMN IF NOT EXISTS start_time DATETIME NULL,
  ADD COLUMN IF NOT EXISTS end_time   DATETIME NULL,
  ADD COLUMN IF NOT EXISTS capacity   INT NULL,
  ADD COLUMN IF NOT EXISTS price      DECIMAL(10,2) NULL,
  ADD COLUMN IF NOT EXISTS description TEXT NULL,
  ADD COLUMN IF NOT EXISTS published  TINYINT(1) NOT NULL DEFAULT 1;

-- Backfill new columns from legacy ones if present
UPDATE events
SET
  start_time = COALESCE(start_time, start_at),
  end_time   = COALESCE(end_time,   end_at),
  price      = COALESCE(price,      price_from);

-- (Optional) drop legacy columns once you’ve confirmed data
-- ALTER TABLE events DROP COLUMN start_at, DROP COLUMN end_at, DROP COLUMN price_from, DROP COLUMN category;

-- Bookings: ensure 'status' exists if you had an older table
ALTER TABLE bookings
  ADD COLUMN IF NOT EXISTS status VARCHAR(32) NOT NULL DEFAULT 'PENDING';
