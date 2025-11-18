-- V9: Drop legacy columns from events now that new ones exist

ALTER TABLE events
  DROP COLUMN start_at,
  DROP COLUMN end_at,
  DROP COLUMN category,
  DROP COLUMN price_from;
