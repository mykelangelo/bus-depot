CREATE TABLE depot_user
(
  id            MEDIUMINT   NOT NULL AUTO_INCREMENT PRIMARY KEY,
  email         VARCHAR(64) NOT NULL UNIQUE,
  password_hash BINARY(60)  NOT NULL
);