CREATE TABLE IF NOT EXISTS gallery_images (
  id BIGINT NOT NULL AUTO_INCREMENT,
  title VARCHAR(160),
  description VARCHAR(255),
  image_url VARCHAR(512) NOT NULL,
  sort_order INT DEFAULT 0,
  is_active BOOLEAN NOT NULL DEFAULT TRUE,
  created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX idx_gallery_active ON gallery_images(is_active);
CREATE INDEX idx_gallery_sort ON gallery_images(sort_order);
