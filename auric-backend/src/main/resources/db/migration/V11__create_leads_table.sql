-- V11__create_leads_table.sql
-- Contact / enquiry leads for Auric Entertainment

CREATE TABLE IF NOT EXISTS leads (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(160) NOT NULL,
  email VARCHAR(160) NOT NULL,
  phone VARCHAR(60),
  event_type VARCHAR(80),
  city VARCHAR(120),
  preferred_date DATE,
  budget VARCHAR(80),
  message TEXT,
  created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

CREATE INDEX IF NOT EXISTS idx_leads_email ON leads(email);
CREATE INDEX IF NOT EXISTS idx_leads_created_at ON leads(created_at);
