ALTER TABLE depot_user
    ADD COLUMN  user_type
        ENUM('driver', 'admin') NOT NULL
        AFTER email;

/*
CREATE TABLE depot_user
(
  id            MEDIUMINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email         VARCHAR(64) NOT NULL UNIQUE,
  user_type     ENUM('driver', 'admin') NOT NULL DEFAULT 'driver',
  password_hash BINARY(60)  NOT NULL
);*/