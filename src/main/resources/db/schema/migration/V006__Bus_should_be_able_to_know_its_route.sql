CREATE TABLE bus
(
  bus_serial VARCHAR(64) NOT NULL UNIQUE PRIMARY KEY,
  route_name VARCHAR(64) NOT NULL,
  FOREIGN KEY (route_name) REFERENCES route (route_name)
);