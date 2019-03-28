CREATE TABLE bus_driver
(
    user_email VARCHAR(64) NOT NULL UNIQUE PRIMARY KEY,
    bus_serial VARCHAR(64) UNIQUE,
    FOREIGN KEY (bus_serial) REFERENCES bus (bus_serial)
);