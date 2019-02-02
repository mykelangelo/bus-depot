INSERT IGNORE INTO depot_user
  (email, user_type, password_hash)
VALUES
  ('administrator@company.com', 'DEPOT_ADMIN','$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy'),
  (       'driver@company.com',  'BUS_DRIVER','$2a$10$IjbakaL8jaGveRFlWFHcLuU00Dc0z3LsUkjrCRNtUia7pSzp3nnyy');
-- password: correctPasswordWhyNotItsAGreatOne

