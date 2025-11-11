-- bcrypt for password: ChangeMe123!  (replace with your own later)
-- You can generate another hash and update it at any time.

INSERT INTO users (email, password, full_name)
VALUES (
  'admin@auric.local',
  '$2a$10$5uA1Oa8vHqXgq8d1m8DPTu2i3mB2m3s3a9k8lK6y6f9H3o7QeXJFa',
  'Auric Admin'
)
ON DUPLICATE KEY UPDATE email = email; -- no-op if already there

INSERT IGNORE INTO user_roles (user_id, role)
SELECT id, 'ADMIN' FROM users WHERE email = 'admin@auric.local';

INSERT IGNORE INTO user_roles (user_id, role)
SELECT id, 'USER' FROM users WHERE email = 'admin@auric.local';
