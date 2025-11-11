-- Schema for Auric Entertainment (MySQL 8+)
-- Engine/charset
SET NAMES utf8mb4;

-- EVENTS
CREATE TABLE IF NOT EXISTS `events` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `venue` VARCHAR(255),
  `start_at` DATETIME,
  `end_at` DATETIME,
  `category` VARCHAR(50),
  `price_from` DECIMAL(10,2),
  `published` TINYINT(1) DEFAULT 1,
  PRIMARY KEY (`id`),
  -- avoid accidental duplicates (same title at same start time)
  UNIQUE KEY `uk_events_title_start` (`title`, `start_at`),
  KEY `idx_events_start_at` (`start_at`),
  KEY `idx_events_published` (`published`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- BOOKINGS
CREATE TABLE IF NOT EXISTS `bookings` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `event_id` BIGINT NOT NULL,
  `full_name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255),
  `phone` VARCHAR(50) NOT NULL,
  `tickets` INT NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_bookings_event_id` (`event_id`),
  CONSTRAINT `fk_bookings_event`
    FOREIGN KEY (`event_id`) REFERENCES `events`(`id`)
    ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
