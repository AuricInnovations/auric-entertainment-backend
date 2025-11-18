-- V8: Add 'status' column to bookings and set default

ALTER TABLE bookings
  ADD COLUMN status VARCHAR(32) NOT NULL DEFAULT 'PENDING';

UPDATE bookings
SET status = COALESCE(status, 'PENDING');
