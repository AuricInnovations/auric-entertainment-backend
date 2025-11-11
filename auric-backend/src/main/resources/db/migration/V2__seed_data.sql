-- Seed EVENTS
INSERT INTO `events` (`title`,`venue`,`start_at`,`end_at`,`category`,`price_from`,`published`)
SELECT 'Auric Live','Kuala Lumpur Convention Centre','2025-06-15 19:00:00','2025-06-15 22:00:00','music',150.00,1
WHERE NOT EXISTS (
  SELECT 1 FROM `events` WHERE `title`='Auric Live' AND `start_at`='2025-06-15 19:00:00'
);

INSERT INTO `events` (`title`,`venue`,`start_at`,`end_at`,`category`,`price_from`,`published`)
SELECT 'Street Food Fest','Petaling Jaya','2025-07-05 10:00:00','2025-07-05 22:00:00','food',10.00,1
WHERE NOT EXISTS (
  SELECT 1 FROM `events` WHERE `title`='Street Food Fest' AND `start_at`='2025-07-05 10:00:00'
);

-- Seed BOOKINGS using titles to resolve event_id
INSERT INTO `bookings` (`event_id`,`full_name`,`email`,`phone`,`tickets`)
SELECT e.id, 'Jane Doe','jane@example.com','+60-12-3456789',2
FROM `events` e
WHERE e.`title`='Auric Live' AND e.`start_at`='2025-06-15 19:00:00'
  AND NOT EXISTS (
    SELECT 1 FROM `bookings` b
    WHERE b.`event_id`=e.id AND b.`full_name`='Jane Doe'
  );

INSERT INTO `bookings` (`event_id`,`full_name`,`email`,`phone`,`tickets`)
SELECT e.id, 'Ali Bin','ali@example.com','+60-11-22223333',4
FROM `events` e
WHERE e.`title`='Street Food Fest' AND e.`start_at`='2025-07-05 10:00:00'
  AND NOT EXISTS (
    SELECT 1 FROM `bookings` b
    WHERE b.`event_id`=e.id AND b.`full_name`='Ali Bin'
  );
