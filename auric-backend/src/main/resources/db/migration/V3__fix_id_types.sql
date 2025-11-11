-- Safely adjust id and FK types for MySQL
ALTER TABLE `bookings` DROP FOREIGN KEY `fk_bookings_event`;

ALTER TABLE `events`
  MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT;

ALTER TABLE `bookings`
  MODIFY COLUMN `id` BIGINT NOT NULL AUTO_INCREMENT,
  MODIFY COLUMN `event_id` BIGINT NOT NULL;

ALTER TABLE `bookings`
  ADD CONSTRAINT `fk_bookings_event`
  FOREIGN KEY (`event_id`) REFERENCES `events`(`id`)
  ON DELETE RESTRICT ON UPDATE CASCADE;
