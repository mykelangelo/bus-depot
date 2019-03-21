INSERT IGNORE INTO depot_user
  (email, user_type, password_hash)
VALUES ('administrator@company.com', 'DEPOT_ADMIN', '$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy'),
       ('driver@company.com', 'BUS_DRIVER', '$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy'),
       ('best.driver@company.com', 'BUS_DRIVER', '$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy'),
       ('some.driver@company.com', 'BUS_DRIVER', '$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy'),
       ('worst.driver@company.com', 'BUS_DRIVER', '$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy'),
       ('hell.o@company.com', 'BUS_DRIVER', '$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy'),
       ('kalibob@company.com', 'BUS_DRIVER', '$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy'),
       ('newbie@company.com', 'BUS_DRIVER', '$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy'),
       ('an.un.en.n@company.com', 'BUS_DRIVER', '$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy'),
       ('zoidberg@company.com', 'BUS_DRIVER', '$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy'),
       ('borax.kid@company.com', 'BUS_DRIVER', '$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy');
-- password: correctPasswordWhyNotItsAGreatOne

INSERT IGNORE INTO route (route_name)
VALUES ('7L'),
       ('72'),
       ('7k'),
       ('71'),
       ('UN7'),
       ('K9');

INSERT IGNORE INTO bus (bus_serial, route_name)
VALUES ('AI7007AA', '72'),
       ('YO7010LO', '72'),
       ('MY1337FV', '71'),
       ('AA2552IA', '7k'),
       ('OA0404OA', '7k'),
       ('Gg6969Gg', '7L'),
       ('DO2019NT', 'UN7'),
       ('DE1373LT', NULL),
       ('UC4444NT', NULL);

INSERT IGNORE INTO bus_driver (user_email, bus_serial, aware_of_assignment)
VALUES ('driver@company.com', 'AI7007AA', TRUE),
       ('best.driver@company.com', 'Gg6969Gg', FALSE),
       ('some.driver@company.com', 'YO7010LO', TRUE),
       ('worst.driver@company.com', 'MY1337FV', TRUE),
       ('hell.o@company.com', 'AA2552IA', TRUE),
       ('kalibob@company.com', NULL, TRUE),
       ('newbie@company.com', NULL, FALSE),
       ('an.un.en.n@company.com', 'DO2019NT', FALSE),
       ('zoidberg@company.com', 'UC4444NT', TRUE),
       ('borax.kid@company.com', NULL, TRUE);