ALTER TABLE depot_user
  ADD COLUMN user_type
    ENUM ('driver', 'admin') NOT NULL
    AFTER email;