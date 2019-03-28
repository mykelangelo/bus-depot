ALTER TABLE bus_driver
  ADD FOREIGN KEY (user_email)
    REFERENCES depot_user (email);