CREATE TABLE events (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  venue VARCHAR(255),
  start_time DATETIME,
  end_time DATETIME,
  capacity INT,
  price DECIMAL(10,2),
  description TEXT,
  published TINYINT(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;