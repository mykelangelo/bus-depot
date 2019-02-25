INSERT IGNORE INTO depot_user
(email, user_type, password_hash)
VALUES ('administrator@company.com', 'DEPOT_ADMIN', '$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy'),
       ('driver@company.com', 'BUS_DRIVER', '$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy'),
       ('hell.o@company.com', 'BUS_DRIVER', '$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy'),
       ('kalibob@company.com', 'BUS_DRIVER', '$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy');
-- password: correctPasswordWhyNotItsAGreatOne

INSERT IGNORE INTO route (route_name)
VALUES ('7L'),
       ('72'),
       ('7k');

INSERT IGNORE INTO bus (bus_serial, route_name)
VALUES ('AI7007AA', '72'),
       ('AA2552IA', '7k'),
       ('OA0404OA', '7k');

INSERT IGNORE INTO bus_driver (user_email, bus_serial)
VALUES ('driver@company.com', 'AI7007AA'),
       ('hell.o@company.com', 'AA2552IA'),
       ('kalibob@company.com', NULL);