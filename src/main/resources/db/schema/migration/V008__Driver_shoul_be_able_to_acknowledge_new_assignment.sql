ALTER TABLE bus_driver
  ADD COLUMN aware_of_assignment
    BOOLEAN NOT NULL
    AFTER bus_serial;